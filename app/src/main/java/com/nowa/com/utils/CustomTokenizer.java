package com.nowa.com.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

/**
 * Created by rafaelfdequeiroz on 19/10/15.
 */
public class CustomTokenizer implements MultiAutoCompleteTextView.Tokenizer {

    @Override
    public int findTokenStart(CharSequence text, int cursor) {
        int position = cursor;
        /*
        while ((text.charAt(position-1) == '@' || text.charAt(position-1) == '#')) {
            position++;
        }
        */

        while (position > 0 && text.charAt(position-1) != ' ') {
            position--;
        }

        return position;
    }

    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        int position = cursor;
        int len = text.length();

        while (position < len) {
            if (text.charAt(position) == ' ') {
                return position;
            } else {
                position++;
            }
        }
        return len;
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        int position = text.length();

        while (position > 0 && text.charAt(position-1) == ' ') {
            position--;
        }

        if (position > 0 &&
                (text.charAt(position-1) == '@' || text.charAt(position-1) == '#')) {
            return text;
        } else {
            if (text instanceof Spanned) {
                SpannableString sp = new SpannableString(text + " ");
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                        Object.class, sp, 0);
                return sp;
            } else {
                return text + " ";
            }
        }
    }
}
