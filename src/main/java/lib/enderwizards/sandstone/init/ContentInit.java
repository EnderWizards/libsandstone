package lib.enderwizards.sandstone.init;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentInit {
   Class itemBlock() default ContentInit.class;
}
