package com.smilehunter.ablebody.ui.cody_item

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smilehunter.ablebody.domain.model.CodyItemFilterBottomSheetTabFilterType
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.PersonHeightFilterType
import com.smilehunter.ablebody.R
import com.smilehunter.ablebody.ui.theme.ABLEBODY_AndroidTheme
import com.smilehunter.ablebody.ui.theme.AbleBlue
import com.smilehunter.ablebody.ui.theme.AbleDeep
import com.smilehunter.ablebody.ui.theme.InactiveGrey
import com.smilehunter.ablebody.ui.theme.SmallTextGrey
import com.smilehunter.ablebody.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodyItemFilterBottomSheet(
    tabFilter: CodyItemFilterBottomSheetTabFilterType,
    onTabFilterChange: (CodyItemFilterBottomSheetTabFilterType) -> Unit,
    genderSelectList: List<Gender>,
    sportItemList: List<HomeCategory>,
    personHeight: PersonHeightFilterType,
    onConfirmRequest: (List<Gender>, List<HomeCategory>, PersonHeightFilterType) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
) {
    val scope = rememberCoroutineScope()
    val genderSelectListState = remember { genderSelectList.toMutableStateList() }
    val sportItemListState = remember { sportItemList.toMutableStateList() }
    var personHeightState by remember { mutableStateOf(personHeight) }

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.height(500.dp),
        containerColor = White,
        sheetState = sheetState
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column {
                CodyItemFilterTabLayout(
                    value = tabFilter,
                    onValueChange = { onTabFilterChange(it) }
                )
                when (tabFilter) {
                    CodyItemFilterBottomSheetTabFilterType.GENDER -> {
                        GenderSelectLayout(
                            itemSelectedList = genderSelectListState,
                            onChangeItem = { gender, checked ->
                                if (checked) {
                                    genderSelectListState.remove(gender)
                                } else {
                                    genderSelectListState.add(gender)
                                }
                            }
                        )
                    }
                    CodyItemFilterBottomSheetTabFilterType.SPORT -> {
                        SportSelectLayout(
                            checkedItemList = sportItemListState,
                            onCheckedChange = { sport, checked ->
                                if (checked) sportItemListState.remove(sport) else sportItemListState.add(sport)
                            }
                        )
                    }
                    CodyItemFilterBottomSheetTabFilterType.PERSON_HEIGHT -> {
                        PersonHeightSelectLayout(
                            value = personHeightState,
                            onValueChange = { personHeightState = it }
                        )
                    }
                }
            }
            CodyFilterBottomSheetBottom(
                modifier = Modifier.align(Alignment.BottomCenter),
                onResetRequest = {
                    genderSelectListState.clear()
                    sportItemListState.clear()
                    personHeightState = PersonHeightFilterType.ALL
                },
                onConfirmRequest = {
                    onConfirmRequest(genderSelectListState, sportItemListState, personHeightState)
                    scope.launch {
                        if (sheetState.currentValue == SheetValue.Expanded && sheetState.hasPartiallyExpandedState) {
                            scope.launch { sheetState.partialExpand() }
                        } else { // Is expanded without collapsed state or is collapsed.
                            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismissRequest() }
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CodyItemFilterBottomSheetPreview() {
    ABLEBODY_AndroidTheme {
        val sheetState = rememberModalBottomSheetState()
        var tabFilter by remember { mutableStateOf(CodyItemFilterBottomSheetTabFilterType.GENDER) }
        val genderSelectList = remember { mutableStateListOf<Gender>() }
        val sportItemList = remember { mutableStateListOf<HomeCategory>() }
        var personHeight by remember { mutableStateOf(PersonHeightFilterType.ALL) }
        CodyItemFilterBottomSheet(
            tabFilter = tabFilter,
            onTabFilterChange =  { tabFilter = it },
            genderSelectList = genderSelectList,
            sportItemList = sportItemList,
            personHeight = personHeight,
            onConfirmRequest = { genderFilterTypeList, sportFilterTypeList, personHeightFilterType ->
                genderSelectList.apply { clear(); addAll(genderFilterTypeList) }
                sportItemList.apply { clear(); addAll(sportFilterTypeList) }
                personHeight = personHeightFilterType
            },
            onDismissRequest = {  },
            sheetState = sheetState,
        )
    }
}

@Composable
private fun CodyItemFilterTabLayout(
    modifier: Modifier = Modifier,
    value: CodyItemFilterBottomSheetTabFilterType,
    onValueChange: (CodyItemFilterBottomSheetTabFilterType) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(CodyItemFilterBottomSheetTabFilterType.values()) { filter ->
            val textColor by animateColorAsState(
                targetValue = if (filter == value) Color(0xFF0C0C0D) else SmallTextGrey
            )
            val interactionSource = remember { MutableInteractionSource() }
            Text(
                text = filter.string,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = textColor,
                ),
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onValueChange(filter) }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CodyItemFilterTabLayoutPreview() {
    ABLEBODY_AndroidTheme {
        var state by remember { mutableStateOf(CodyItemFilterBottomSheetTabFilterType.GENDER) }
        CodyItemFilterTabLayout(
            value = state,
            onValueChange = { state = it }
        )
    }
}

@Composable
private fun CodyFilterBottomSheetBottom(
    modifier: Modifier = Modifier,
    onResetRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onResetRequest,
            shape = RoundedCornerShape(size = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
            ),
            border = BorderStroke(width = 1.dp, color = InactiveGrey),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cody_tab_reset),
                contentDescription = "reset button",
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 2.dp)
            )
            Text(
                text = "초기화",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = AbleDeep,
                    textAlign = TextAlign.Justify,
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
        Button(
            onClick = onConfirmRequest,
            shape = RoundedCornerShape(size = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AbleBlue,
            ),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "필터 적용",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(500),
                    color = White,
                    textAlign = TextAlign.Justify,
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CodyFilterBottomSheetBottomPreview() {
    CodyFilterBottomSheetBottom(
        onResetRequest = {  },
        onConfirmRequest = {  }
    )
}

@Composable
private fun GenderSelectLayout(
    modifier: Modifier = Modifier,
    itemSelectedList: SnapshotStateList<Gender>,
    onChangeItem: (Gender, Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (gender in Gender.values()) {
            val isContains by remember { derivedStateOf { itemSelectedList.contains(gender) } }
            val textColor by animateColorAsState(
                targetValue = if (isContains) AbleBlue else Color(0xFF505863)
            )
            val backgroundColor by animateColorAsState(
                targetValue = if (isContains) Color(0xFFE9F1FE) else White
            )
            val strokeColor by animateColorAsState(
                targetValue = if (isContains) AbleBlue else Color(0xFFCCE1FF)
            )
            val interactionSource = remember { MutableInteractionSource() }
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onChangeItem(gender, isContains) }
                    ),
                color = backgroundColor,
                border = BorderStroke(width = 1.dp, color = strokeColor),
                shape = RoundedCornerShape(size = 4.dp),
            ) {
                Text(
                    text = stringResource(id = gender.resourceID),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_cjk_kr_regular)),
                        fontWeight = FontWeight(400),
                        color = textColor,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderSelectLayoutPreview() {
    ABLEBODY_AndroidTheme {
        val state = remember { mutableStateListOf<Gender>() }
        GenderSelectLayout(
            itemSelectedList = state,
            onChangeItem = { gender, checked ->
                if (checked) state.remove(gender) else state.add(gender)
            }
        )
    }
}

@Composable
private fun SportSelectLayout(
    modifier: Modifier = Modifier,
    checkedItemList: List<HomeCategory>,
    onCheckedChange: (HomeCategory, Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SportSelectItem(
            checked = checkedItemList.contains(HomeCategory.GYMWEAR),
            text = "웨이트",
            onClick = { checked -> onCheckedChange(HomeCategory.GYMWEAR, checked) }
        )
        SportSelectItem(
            checked = checkedItemList.contains(HomeCategory.PILATES),
            text = "필라테스",
            onClick = { checked -> onCheckedChange(HomeCategory.PILATES, checked) }
        )
        SportSelectItem(
            checked = checkedItemList.contains(HomeCategory.RUNNING),
            text = "러닝",
            onClick = { checked -> onCheckedChange(HomeCategory.RUNNING, checked) }
        )
        SportSelectItem(
            checked = checkedItemList.contains(HomeCategory.TENNIS),
            text = "테니스",
            onClick = { checked -> onCheckedChange(HomeCategory.TENNIS, checked) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SportSelectLayoutPreview() {
    SportSelectLayout(
        checkedItemList = listOf(),
        onCheckedChange = { sport, checked -> }
    )
}

@Composable
private fun SportSelectItem(
    checked: Boolean,
    text: String,
    onClick: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick(checked) }
        )
    ) {
        val checkBoxResourceID = if (checked) {
            R.drawable.ic_product_item_filter_bottom_sheet_check_box_enable
        } else {
            R.drawable.ic_product_item_filter_bottom_sheet_check_box_disable
        }
        Image(
            painter = painterResource(id = checkBoxResourceID),
            contentDescription = "check box"
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
    }
}
@Composable
private fun PersonHeightSelectLayout(
    modifier: Modifier = Modifier,
    value: PersonHeightFilterType,
    onValueChange: (PersonHeightFilterType) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PersonHeightSelectItem(
            checked = value == PersonHeightFilterType.ALL,
            text = "전체",
            onClick = { onValueChange(PersonHeightFilterType.ALL) }
        )
        PersonHeightSelectItem(
            checked = value == PersonHeightFilterType.FROM_150_TO_160,
            text = "150~160cm",
            onClick = { onValueChange(PersonHeightFilterType.FROM_150_TO_160) }
        )
        PersonHeightSelectItem(
            checked = value == PersonHeightFilterType.FROM_160_TO_170,
            text = "160~170cm",
            onClick = { onValueChange(PersonHeightFilterType.FROM_160_TO_170) }
        )
        PersonHeightSelectItem(
            checked = value == PersonHeightFilterType.FROM_170_TO_180,
            text = "170~180cm",
            onClick = { onValueChange(PersonHeightFilterType.FROM_170_TO_180) }
        )
        PersonHeightSelectItem(
            checked = value == PersonHeightFilterType.FROM_180_TO_190,
            text = "180~190cm",
            onClick = { onValueChange(PersonHeightFilterType.FROM_180_TO_190) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonHeightSelectLayoutPreview() {
    var state by remember { mutableStateOf(PersonHeightFilterType.ALL) }
    PersonHeightSelectLayout(
        value = state,
        onValueChange = { height ->
            state = height
        }
    )
}

@Composable
fun PersonHeightSelectItem(
    checked: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        val checkBoxResourceID = if (checked) {
            R.drawable.ic_product_item_filter_bottom_sheet_toggle_box_enable
        } else {
            R.drawable.ic_product_item_filter_bottom_sheet_toggle_box_disable
        }
        Image(
            painter = painterResource(id = checkBoxResourceID),
            contentDescription = "check box"
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}