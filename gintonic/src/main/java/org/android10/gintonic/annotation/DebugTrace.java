
package org.android10.gintonic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表明被注释的方法将被跟踪（仅在Debug模式下）并且将会与Aspect程序中截获该注释的Advise关联，调用该切点
 * 的Advise
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface DebugTrace {}
