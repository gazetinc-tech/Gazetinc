package com.gazetinc.shopninja.Utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gazetinc.shopninja.R;


public class CustomEditText extends LinearLayout {

    private EditText value;
    private TextView error;
    private ImageView drawableleft;
    private ImageView drawableright;
    private TextView drawabletext;
    private Context context;
    private RelativeLayout relative_custom;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Options, 0, 0);
        String text = a.getString(R.styleable.Options_text);
        String hint = a.getString(R.styleable.Options_hint);
        String err = a.getString(R.styleable.Options_error);
        String type = a.getString(R.styleable.Options_types);
        boolean left = a.getBoolean(R.styleable.Options_leftdrawable, false);
        int icon = a.getResourceId(R.styleable.Options_icon, 0);
        boolean right = a.getBoolean(R.styleable.Options_rightdrawable, false);
        boolean textdrawable = a.getBoolean(R.styleable.Options_textdrawable, false);
        boolean editable = a.getBoolean(R.styleable.Options_editable, true);


        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_cusomt_titlevalue, this, true);


        relative_custom = (RelativeLayout) view.findViewById(R.id.relative_custom);
        value = (EditText) view.findViewById(R.id.value);
        value.setFocusable(editable);
        value.setClickable(!editable);


        drawableleft = (ImageView) view.findViewById(R.id.drawableleft);
        drawableright = (ImageView) view.findViewById(R.id.drawableright);
        drawabletext = (TextView) view.findViewById(R.id.browseplans);

        value.setText(text);
        value.setHint(hint);

        if (icon == 0) {
            drawableleft.setVisibility(View.GONE);
        } else {
            drawableleft.setImageResource(icon);
            drawableleft.setVisibility(View.VISIBLE);
        }


        if (textdrawable) {
            drawabletext.setVisibility(View.VISIBLE);
        } else {
            drawabletext.setVisibility(View.GONE);
        }


        switch (type) {
            case "text":
                value.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case "number":
                value.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "password":
                value.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case "capital":
                value.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_VARIATION_FILTER);
                break;
            case "email":
                value.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
        }
        value.setTypeface(Typeface.SERIF);
        error = (TextView) view.findViewById(R.id.error);
        error.setText(err);
    }

    public void setHint(String hint) {
        value.setHint(hint);
    }

    public void setFilters(int length) {
        value.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }


    public String getValue() {

        if (value.getText().toString().length() > 1) {
            hideError();
        }
        return value.getText().toString();
    }

    public void setValue(String s) {
        value.setText(s);
        value.setSelection(s.length());
    }


    public String getError() {
        return error.getText().toString();
    }

    public void setError(String s) {
        error.setText(s);
        error.setVisibility(View.VISIBLE);
        value.requestFocus();
        relative_custom.setBackground(context.getResources().getDrawable(R.drawable.background_round_red_corner));
    }

    public void hideError() {
        error.setVisibility(View.GONE);
        relative_custom.setBackground(context.getResources().getDrawable(R.drawable.background_round_grey_corner));
    }

    public String getHint() {
        return value.getHint().toString();
    }

    public CustomEditText(Context context) {
        this(context, null);
    }

}


