package org.cuspid.constant;

/**
 * @author Do Quoc Viet
 * The template for the generation
 */

public final class Template {

    /**
     * Repository template
     */
    public static final String REPOSITORY_TEMPLATE;

    /**
     * Service interface template
     */
    public static final String SERVICE_INTERFACE_TEMPLATE;

    /**
     * Service implementation template
     */
    public static final String SERVICE_IMPL_TEMPLATE;

    /**
     * Api template
     */
    public static final String API_TEMPLATE;

    /**
     * Controller template
     */
    public static final String CONTROLLER_TEMPLATE;

    static {
        REPOSITORY_TEMPLATE = """
                package org.cuspid.generated.repository;
                                                    
                import %s;
                import %s;
                import org.springframework.data.jpa.repository.JpaRepository;
                import org.springframework.stereotype.Repository;
                                            
                /**
                 * @author Do Quoc Viet
                 * This class is generated from Cuspid Api Generator.
                 * Please keep it in mind that don't modify this class directly.
                 */
                                            
                @Repository
                public interface Cupid%sRepository extends JpaRepository<%s, %s> {
                }
                """;

        SERVICE_INTERFACE_TEMPLATE = """
                """;

        SERVICE_IMPL_TEMPLATE = """
                """;

        API_TEMPLATE = """
                """;

        CONTROLLER_TEMPLATE = """
                """;
    }

    private Template() {
    }
}
