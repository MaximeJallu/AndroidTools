
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IN LIB
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiKeyService {
    /**
     * Ex: "cube_assembler", "stores", "interact" ...
     * @return la key name de la api cible
     */
    String value();

    /**
     * ex "proxy_v3", "cube", "integ", "stores", "open_voice" ...
     * @return la clé de la base url cible
     */
    String targetUrl();
    boolean addCache() default false;
    boolean allowCookies() default false;
}