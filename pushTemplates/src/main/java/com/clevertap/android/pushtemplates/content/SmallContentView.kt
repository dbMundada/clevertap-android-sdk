package com.clevertap.android.pushtemplates.content

import android.content.Context
import com.clevertap.android.pushtemplates.R
import com.clevertap.android.pushtemplates.TemplateRenderer

open class SmallContentView(context: Context, layoutId: Int = R.layout.content_view_small,
    renderer: TemplateRenderer): ContentView(context,layoutId,renderer) {

    init {
        setCustomContentViewBasicKeys()
        setCustomContentViewTitle(renderer.pt_title)
        setCustomContentViewMessage(renderer.pt_msg)
        setCustomContentViewCollapsedBackgroundColour(renderer.pt_bg)
        setCustomContentViewTitleColour(renderer.pt_title_clr)
        setCustomContentViewMessageColour(renderer.pt_msg_clr)
        setCustomContentViewSmallIcon()
        setCustomContentViewDotSep()
        setCustomContentViewLargeIcon(renderer.pt_large_icon)
    }
}