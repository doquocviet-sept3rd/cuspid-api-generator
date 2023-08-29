package org.cuspid.constant;

/**
 * @author Do Quoc Viet
 * The template for the generation
 */

public final class Template {

    /**
     * Repository interface template
     */
    public static final String REPOSITORY_INTERFACE_TEMPLATE;

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
        REPOSITORY_INTERFACE_TEMPLATE = """
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
                @SuppressWarnings("unused")
                public interface Cupid%sRepository extends JpaRepository<%s, %s> {
                }
                """;

        SERVICE_INTERFACE_TEMPLATE = """
                package org.cupid.generated.service;
                                
                import %s;
                import %s;
                import java.util.List;
                import java.util.Optional;
                                
                /**
                 * @author Do Quoc Viet
                 * This class is generated from Cuspid Api Generator.
                 * Please keep it in mind that don't modify this class directly.
                 */
                                
                @SuppressWarnings("unused")
                public interface Cupid%sService {
                                
                    /**
                     * Get entities
                     *
                     * @return entities found entities
                     */
                    List<%s> getEntities();
                    
                    /**
                     * Get entity by id
                     *
                     * @param id entity's id
                     * @return entity found entity
                     */
                    Optional<%s> getEntityById(%s id);
                     
                    /**
                     * Create entity
                     *
                     * @param entity entity need to be created
                     * @return entity created entity
                     */
                    %s createEntity(%s entity);
                     
                    /**
                     * Create entities
                     *
                     * @param entities entities need to be created
                     * @return entities created entities
                     */
                    List<%s> createEntities(List<%s> entities);
                     
                    /**
                     * Update entity
                     *
                     * @param entity entity need to be updated
                     * @return entity updated entity
                     */
                    %s updateEntity(%s entity);
                     
                    /**
                     * Update entities
                     *
                     * @param entities entities need to be updated
                     * @return entities updated entities
                     */
                    List<%s> updateEntities(List<%s> entities);
                     
                    /**
                     * Delete entity by id
                     *
                     * @param id entity's id need to be deleted
                     * @return entity deleted entity
                     */
                    %s deleteEntityById(%s id);
                     
                    /**
                     * Delete entities by ids
                     *
                     * @param ids entities ids need to be deleted
                     * @return entities deleted entities
                     */
                    List<%s> deleteEntitiesByIds(List<%s> ids);
                                
                }
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
