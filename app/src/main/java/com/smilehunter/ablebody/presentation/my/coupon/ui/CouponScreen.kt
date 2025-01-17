package com.smilehunter.ablebody.presentation.my.coupon.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smilehunter.ablebody.R
import com.smilehunter.ablebody.domain.usecase.CouponStatus
import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.domain.model.ErrorHandlerCode
import com.smilehunter.ablebody.presentation.my.coupon.CouponViewModel
import com.smilehunter.ablebody.ui.theme.AbleBlue
import com.smilehunter.ablebody.ui.theme.AbleDark
import com.smilehunter.ablebody.ui.theme.LightShaded
import com.smilehunter.ablebody.ui.theme.PlaneGrey
import com.smilehunter.ablebody.ui.theme.SmallTextGrey
import com.smilehunter.ablebody.ui.utils.AbleBodyAlertDialog
import com.smilehunter.ablebody.ui.utils.BackButtonTopBarLayout
import com.smilehunter.ablebody.ui.utils.SimpleErrorHandler
import com.smilehunter.ablebody.utils.nonReplyClickable

@Composable
fun CouponRoute(
    couponViewModel: CouponViewModel = hiltViewModel(),
    onErrorOccur: (ErrorHandlerCode) -> Unit,
    couponRegisterOnClick: () -> Unit,
    onBackRequest: () -> Unit
) {
    val couponData by couponViewModel.couponListLiveData.observeAsState()
    val errorData by couponViewModel.sendErrorLiveData.observeAsState()

    LaunchedEffect(key1 = true) {
        couponViewModel.getCouponData()
    }
    CouponScreen(
        onBackRequest = onBackRequest,
        couponData?.size ?: 0,
        couponData ?: emptyList(),
        couponRegisterOnClick = couponRegisterOnClick
    )

    SimpleErrorHandler(
        refreshRequest = { couponViewModel.getCouponData() },
        onErrorOccur = onErrorOccur,
        isError = errorData != null,
        throwable = errorData
    )
}

@Composable
fun CouponScreen(
    onBackRequest: () -> Unit,
    size: Int,
    couponData: List<CouponData>,
    couponRegisterOnClick: () -> Unit
) {
    Log.d("couponData", couponData.toString())
    Scaffold(
        topBar = {
            BackButtonTopBarLayout(onBackRequest = onBackRequest)
            Text(
                text = "쿠폰",
                modifier = Modifier.padding(horizontal = 50.dp, vertical = 15.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Button(
                onClick = {
                    couponRegisterOnClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .padding(end = 14.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = "쿠폰 등록",
                    fontSize = 14.sp
                )
            }
            for (i in 0 until size) {
                CouponContent(
                    couponData[i].invalid,
                    couponData[i].couponTitle,
                    couponData[i].couponCount,
                    couponData[i].expirationDate,
                    couponData[i].content
                )
            }
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
fun CouponContent(
    isValid: Boolean,
    couponTitle: String,
    couponCount: Int,
    expirationDateStr: Int,
    content: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 17.dp, start = 15.dp, end = 15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(15.dp)
                .fillMaxSize()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = couponTitle,
                        style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 26.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_bold)),
                            fontWeight = FontWeight(700),
                            color = AbleDark,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                    UsageStatusButton(isValid)
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (couponCount != 0) {
                        Text(
                            text = "${couponCount}명 남음",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_regular)),
                                fontWeight = FontWeight(500),
                                color = AbleBlue,
                                textAlign = TextAlign.Center,
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            ),
                        )

                        Spacer(modifier = Modifier.size(10.dp))
                    }

                    Text(
                        text = "${expirationDateStr}일 남음",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_regular)),
                            fontWeight = FontWeight(500),
                            color = AbleDark,
                            textAlign = TextAlign.Center,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        ),
                    )
                }

                Spacer(modifier = Modifier.size(23.dp))
                Text(
                    text = content,
                    style = TextStyle(
                        fontSize = 19.sp,
                        lineHeight = 26.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_bold)),
                        fontWeight = FontWeight(700),
                        color = AbleBlue,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    )
                )
            }
        }
    }
}


@Composable
fun UsageStatusButton(isValid: Boolean) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isValid) PlaneGrey else LightShaded
        ),
        modifier = Modifier
            .width(100.dp)
            .height(30.dp)
    ) {
        Text(
            text = if (isValid) "사용완료" else "사용가능",
            fontSize = 11.sp,  // 글자 크기를 조절합니다.
            color = if (isValid) SmallTextGrey else AbleBlue,
            modifier = Modifier
                .padding(horizontal = 8.dp)  // 좌우 여백을 조절합니다.
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CouponPreview() {
    CouponScreen(
        {},
        3,
        couponData = listOf(
            CouponData(
                1,
                "brand",
                "선착순 100명 3,000원 할인 쿠폰",
                "3,000원 할인",
                false,
                CouponData.CouponType.USER,
                CouponData.DiscountType.PRICE,
                55,
                19,
                3000
            )
        ),
        {})
}

@Composable
fun CouponRegisterRoute(
    couponViewModel: CouponViewModel = hiltViewModel(),
    onErrorOccur: (ErrorHandlerCode) -> Unit,
    onBackRequest: () -> Unit
) {
    val errorData by couponViewModel.sendErrorLiveData.observeAsState()

    CouponRegisterScreen(onBackRequest = onBackRequest)

    SimpleErrorHandler(
        refreshRequest = { couponViewModel.getCouponData() },
        onErrorOccur = onErrorOccur,
        isError = errorData != null,
        throwable = errorData
    )
}

@Composable
fun CouponRegisterScreen(
    onBackRequest: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackButtonTopBarLayout(onBackRequest = onBackRequest)
            Text(
                text = "쿠폰 등록",
                modifier = Modifier.padding(horizontal = 70.dp, vertical = 15.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "쿠폰 등록",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_bold)),
                            fontWeight = FontWeight(500),
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        ),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.information_icon), // 변환된 벡터 드로어블 리소스 ID
                        contentDescription = "Information Icon",
                        tint = SmallTextGrey,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .nonReplyClickable { showDialog = true }

                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                CouponNumberTextField()
            }
        }
    }
    if (showDialog) {
        CouponRegisterDialog({ showDialog = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponNumberTextField(
    couponViewModel: CouponViewModel = hiltViewModel()
) {
    var inputText by remember {
        mutableStateOf("")
    }

    var couponRegisterDialog by remember { mutableStateOf(false) }
    val couponState by couponViewModel.couponStateLiveData.observeAsState()

    Box(
        modifier = Modifier
            .border(1.dp, color = SmallTextGrey)
    ) {
        Row(
            modifier = Modifier.height(52.dp)
        ) {
            androidx.compose.material3.TextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                modifier = Modifier.weight(10.5f),
                placeholder = {
                    Text(
                        text = "쿠폰 번호를 입력하세요",
                        fontSize = 12.sp,
                        color = SmallTextGrey
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        couponViewModel.couponRegister(inputText)
                    }
                    couponRegisterDialog = true

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(4.5f),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    text = "쿠폰 등록",
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
    if (couponRegisterDialog) {
        CouponPopup(
            onDismiss = { couponRegisterDialog = false },
            message = when (couponState) {
                CouponStatus.SUCCESS -> "쿠폰 등록이 완료되었어요."
                null -> "쿠폰 번호를 입력해주세요."
                else -> "쿠폰 번호를 확인해주세요."
            }
        )
    }

}

@Composable
fun CouponRegisterDialog(
    onDismiss: () -> Unit
) {
    AbleBodyAlertDialog(
        onDismissRequest = { onDismiss() },
        positiveButtonOnClick = { onDismiss() },
        negativeButtonOnClick = { },
        positiveText = "확인"
    ) {
        androidx.compose.material.Text(
            text = "\uD83D\uDCCC 쿠폰 등록은 무엇인가요?",
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_bold)),
                fontWeight = FontWeight(500),
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
        androidx.compose.material.Text(
            text = "각종 이벤트를 통해 쿠폰번호가 제공되며, 쿠폰번호를 입력하여 쿠폰을 등록할 수 있습니다.",
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun CouponPopup(
    onDismiss: () -> Unit,
    message: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                androidx.compose.material.Text(
                    text = message,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_bold)),
                        fontWeight = FontWeight(500),
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        },
        buttons = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AbleBlue),
                    modifier = Modifier
                        .width(170.dp)
                        .height(45.dp)
                        .padding(start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    androidx.compose.material.Text(text = "확인", color = Color.White)
                }
            }
        },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .width(329.dp)
            .height(120.dp)
            .padding(0.dp)
    )

}

@Preview
@Composable
fun CouponRegisterScreenPreview() {
    CouponRegisterScreen({})
}

@Preview
@Composable
fun CouponRegisterDialogPreview() {
    CouponRegisterDialog({})
}

@Preview(showBackground = true)
@Composable
fun CouponPopupPreview() {
    Column {
        CouponPopup({}, "쿠폰 번호를 입력해주세요.")
        CouponPopup({}, "쿠폰 번호를 확인해주세요.")
        CouponPopup({}, "쿠폰 등록이 완료되었어요.")
    }
}