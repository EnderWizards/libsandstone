package lib.enderwizards.sandstone.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SandstoneMod {
    String basePackage() default "";
    String itemsLocation() default "items";
    String blocksLocation() default "blocks";
}
