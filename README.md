<div align="center">
  <img src="https://github.com/user-attachments/assets/f24481e5-2563-4e22-8bae-027c26d8ade5">  
  
  운동복 쇼핑 및 커뮤니티 Android 프로젝트<br />  

  ---
  
  <img src="https://github.com/user-attachments/assets/128c7d93-ed8c-4085-a968-3e202283a913" width=30% height=30%/>  
  <img src="https://github.com/user-attachments/assets/11a2e603-d3de-441d-ae61-8d1128bf1135" width=30% height=30%/>  
  <img src="https://github.com/user-attachments/assets/93cc0ca2-ef63-4a53-bf06-6fc78246f6f8" width=30% height=30%/>
</div>  

# 기술 스택
![image](https://github.com/user-attachments/assets/f2ab2244-46b9-437d-aec5-455e94be3937)

# 아키텍처
![arch1](https://github.com/user-attachments/assets/5a1f6bb4-8a23-4809-a82c-9745d56517c6)  
해당 프로젝트는 Clean Architecture를 기반으로 설계되었습니다.  
Clean Architecture는 도메인 중심 설계 아키텍처로 Presentation Layer와 Data Layer가 Domain을 의존하는 형태로 개발하였습니다.

## Data Layer
![arch2](https://github.com/user-attachments/assets/de33a05b-300d-4aee-8f3d-dcc43a85e39d)  
데이터 계층은 Repository 패턴으로 설계되며, DB 쿼리 및 네트워크 작업을 처리합니다.  
또한, 오프라인 및 캐싱 작업도 이 계층에서 수행합니다.

## Domain Layer
![arch3](https://github.com/user-attachments/assets/abcb2790-eb94-416d-9332-5e575167d911)

도메인 계층은 비지니스 로직이 존재하는 계층입니다. 
핵심 업무 규칙인 Entity가 존재하며, UseCase는 Entity의 데이터의 흐름을 조정하도록 설계하였습니다.

또한 Repository 의존성 역전을 통해서 Data Layer의 의존 하지 않는 형태로 진행하였습니다.

## Presentation Layer
![arch4](https://github.com/user-attachments/assets/83515b35-6935-4970-a72d-d11f3d985b5d)

프레젠테이션 계층은 안드로이드에서 권장하는 단방향 데이터 흐름(UDF)을 따르고 있습니다.

**Event**

Button, CheckBox, Switch, Tabs 등의 UI 요소에서 이벤트가 발생하면 ViewModel을 호출합니다.

**UI State**

UI 상태에 따라 Loading, Error, Success 등으로 분기 처리하여, UI State(ViewModel)에 따라 사용자에게 표시합니다.  


# 트러블슈팅

## 상품 태그를 배치하면서 생겼던 어려움점들

크리에이터가 게시한 사진에 상품 태그를 입력해야 하는 상황이 있었습니다. 
서버에서 제공해주는 좌표에 맞게 상품 태그를 표시해야 했지만, 일반적인 Jetpack Compose Layout으로는 불가능하여서 Custom Layout을 활용하여 좌표에 맞게 상품 태그를 표시하는 방법을 구현했습니다.  

- 이미지  
  <img src="https://github.com/user-attachments/assets/38f268c7-1a00-4f1d-8c89-ba5b8ab74d42" width=30% height=30%/>

- 코드
  
    
    ```kotlin
     1  @Composable
     2  fun CreatorDetailProductItemTagLayout(
     3      imageContent: @Composable () -> Unit,
     4      productTageContent: @Composable () -> Unit,
     5      offsetList: List<Offset>
     6  ) {
     7      Layout(
     8          contents = listOf(imageContent, productTageContent),
     9          measurePolicy = { (imageContentMeasurableList, productTageContentMeasurableList), constraint ->
    10              val imagePlaceable = imageContentMeasurableList.first().measure(constraint)
    11              val productItemTagPlaceable = productTageContentMeasurableList.map { it.measure(constraint) }
    12  
    13              layout(imagePlaceable.width, imagePlaceable.height) {
    14                  imagePlaceable.place(0, 0)
    15  
    16                  productItemTagPlaceable.mapIndexed { index, placeable ->
    17                      placeable.place(
    18                          x = (offsetList[index].x * imagePlaceable.width.toDp() - (placeable.width.toDp() / 2)).toPx().roundToInt(),
    19                          y = (offsetList[index].y * imagePlaceable.width.toDp() - (placeable.height.toDp() / 2) ).toPx().roundToInt()
    20                      )
    21                  }
    22              }
    23          }
    24      )
    25  }
    
    // https://github.com/AblebodyAndroid/App/blob/bb85673b70b0f4848d338bdb17a516a882be5363/app/src/main/java/com/smilehunter/ablebody/presentation/creator_detail/ui/CreatorDetailScreen.kt#L864-L888
    ```
    
1. **Custom Layout 사용**: @Composable 함수를 **imageContent**와 **productTageContent**를 각각 나누어  인자로 받았습니다.
2. **부모 Layout 배치**: imageContent composable layout의 넓이와 높이를 각각 배치했습니다.
3. **상품 태그 표시**: 레이아웃 위에 상품 태그들의 Offset 리스트를 받아서 좌표에 맞게 태그를 표시했습니다.

이렇게 Custom Layout을 활용하여 서버에서 제공하는 좌표에 맞게 상품 태그를 정확히 표시할 수 있었습니다.

(BoxWithConstraints 출시 전 입니다)

## Repository를 SingletonComponents로 (무조건)선언했던 문제들

프로젝트 초기에는 Dagger와 Hilt를 제대로 이해하지 못해 모든 모듈에 **SingletonComponent**를 선언하는 문제가 있었습니다

모듈에 사용하는 생명주기에 맞게 다음과 같이 수정하였습니다 :

![hilt](https://github.com/user-attachments/assets/d036aec2-6508-4099-8a28-b3ffb16213c8)


문제를 수정하여 메모리 낭비와 디바이스의 Cold boot 시간을 단축할 수 있었습니다 

## 자주 변경되는 서버의 데이터의 해결하는 과정들

초반에 아키텍처의 제대로 된 설계 없이 persenation Layer가 Network Model (or Network DTO)를 의존하는 문제가 있었습니다.

이러한 문제는 서버가 변경될 때마다 앱 단에서 대규모 리팩토링을 야기했고 이러한 문제를 해결하기 위해 도메인 중심 설계(DDD) 아키텍처인 **Clean Architecture**를 도입시켰습니다.

## 에러 처리 하면서 생각 했던 과정들  

초반에는 **`Sealed interface`**를 감싸서 **`Result<Data>`**를 계층마다 판단하는 방식을 사용했습니다. 

하지만, 계층마다 **`Sealed interface`**를 판단하는 것은 옳지 않다고 판단했습니다. 

그 이유는 데이터 판단 코드들이 보일러플레이트 로직과 중복된 코드 등 여러 문제가 발생했기 때문입니다. 

또한, 이러한 방식은 코드에서 **`throw`**를 제대로 이해하지 못한 것이라고 생각했습니다.

따라서, **`ViewModel`**에서 에러를 **`catch`**한 후, UI 상태에 따라 판단하는 것이 더 좋은 방향이라고 결론지었습니다.  

# 이미지

### 운동복 브랜드 목록
<img src="https://github.com/user-attachments/assets/56a85a1c-54cc-42a2-b882-706460c4525c" width="200"/>  

### 카테고리 별 아이템 필터링
<img src="https://github.com/user-attachments/assets/41db3cca-4f0d-4db3-bcd6-4d924b12afd0" width="200"/>  
<img src="https://github.com/user-attachments/assets/ab4755d5-1914-45e1-83f5-87dca85aacd5" width="200"/>  

### 유저 코디 게시물
<img src="https://github.com/user-attachments/assets/acc9c846-b8e1-4b98-a873-32bd03981fb4" width="200"/>  

### 아이템 상세 화면
<img src="https://github.com/user-attachments/assets/53442d1d-643d-4070-8917-a19d4af613fa" width="200"/>
<img src="https://github.com/user-attachments/assets/8c14f184-d102-41c0-87f8-d4f4fd71f56c" width="200"/>

### 북마크
<img src="https://github.com/user-attachments/assets/05999fec-a748-4087-9abe-b2279177a51e" width="200"/>

### 마이 페이지
<img src="https://github.com/user-attachments/assets/7f5c9b45-0ba8-4157-a0a9-c9d4e77ccac2" width="200"/>


# 시연영상
https://youtu.be/wuBvWVeh-0c
