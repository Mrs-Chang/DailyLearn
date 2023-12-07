package com.chang.dailylearn.words;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

public class SpannableBuilder {
    private SpannableStringBuilder ssb = new SpannableStringBuilder();
    private SpannableString spannableString;

    public SpannableStringBuilder getSpannable() {
        return ssb;
    }

    public SpannableBuilder() {
    }

    public SpannableBuilder(String init) {
        spannableString = new SpannableString(init);
    }

    public SpannableBuilder nextSpannable(String text) {
        if (spannableString != null) {
            ssb.append(spannableString);
        }
        spannableString = new SpannableString(text);
        return this;
    }

    public SpannableStringBuilder finish() {
        ssb.append(spannableString);
        return ssb;
    }

    public SpannableBuilder setColor(int color) {
        spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder setTextSize(int size) {
        spannableString.setSpan(new AbsoluteSizeSpan(size, false), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder setRelativeSize(float proportion) {
        spannableString.setSpan(new RelativeSizeSpan(proportion), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * @param typeface {@see Typeface.BOLD} {@link Typeface}
     * @return
     */
    public SpannableBuilder setTypeface(int typeface) {
        spannableString.setSpan(new StyleSpan(typeface), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder setStyle(CharacterStyle style) {
        spannableString.setSpan(style, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableBuilder setImage(@NonNull Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);
        spannableString.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }
}
