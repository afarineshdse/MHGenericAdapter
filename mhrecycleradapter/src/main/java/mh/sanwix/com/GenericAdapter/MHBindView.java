package mh.sanwix.com.GenericAdapter;


import android.content.Context;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by m.hoseini on 8/26/2017.
 */

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MHBindView
{
    /**
     * gets view id
     * @return view id
     */
    int value();

    /**
     * marks view as clickable
     * @return isClickable ? true : false;
     */
    boolean isClickable() default false;
}
