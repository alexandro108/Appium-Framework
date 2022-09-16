package com.example.learning_english_kotlin.appium



enum class ViewId(
    override val ios: String,
    override val iosMatchType: IosMatchType,
    override val android: String,
    override val androidMatchType: AndroidMatchType
) : IViewId {

    MENU(
    "",
    IosMatchType.ID,
    "Семья и друзья",
    AndroidMatchType.ID

    ),
    START(
        "",
        IosMatchType.ID,
        "btn_start_learning",
        AndroidMatchType.ID

    ),
    PICTURE(
        "",
        IosMatchType.ID,
        "ivPicture",
        AndroidMatchType.ID

    ),
    NEXT(
        "",
        IosMatchType.ID,
        "btn_forward",
        AndroidMatchType.ID

    ),
}

enum class IosMatchType {
    TEXT,
    ID,
    NAME,
    TYPE,
    XPATH
}

enum class AndroidMatchType {
    DESCRIPTION,
    TEXT,
    TEXT_IGNORE_CASE,
    ID,
    XPATH
}

class AndroidViewId(text: String, matchType: AndroidMatchType): IViewId {
    override val ios: String = ""
    override val iosMatchType: IosMatchType = IosMatchType.ID
    override val android: String = text
    override val androidMatchType: AndroidMatchType = matchType
}

interface IViewId {
    val ios: String
    val iosMatchType: IosMatchType
    val android: String
    val androidMatchType: AndroidMatchType




}