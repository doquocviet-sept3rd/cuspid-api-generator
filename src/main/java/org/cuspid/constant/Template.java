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
                package org.cupid.generated.service;
                                
                import ${entityName};
                import ${entityIdName};
                import java.util.List;
                import java.util.Optional;
                import org.springframework.stereotype.Service;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.cuspid.generated.repository.Cuspid${entityClassSimpleName}Repository;
                                
                /**
                 * @author Do Quoc Viet
                 * This class is generated from Cuspid Api Generator.
                 * Please keep it in mind that don't modify this class directly.
                 */
                                
                @Service
                @SuppressWarnings("unused")
                public class Cupid${entityClassSimpleName}ServiceImpl implements Cupid${entityClassSimpleName}Service {
                                
                    @Autowired
                    private Cupid${entityClassSimpleName}Repository cuspid${entityClassSimpleName}Repository;
                                
                    @Override
                    public List<${entityClassSimpleName}> getEntities() {
                        return cuspid${entityClassSimpleName}Repository.findAll();
                    }
                    
                    @Override
                    public Optional<${entityClassSimpleName}> getEntityById(${entityClassSimpleName} id) {
                        return cuspid${entityClassSimpleName}Repository.findById(id);
                    }
                    
                    @Override
                    public ${entityClassSimpleName} createEntity(${entityClassSimpleName} entity) {
                        return cuspid${entityClassSimpleName}Repository.save(entity);
                    }
                    
                    @Override
                    public List<${entityClassSimpleName}> createEntities(List<${entityClassSimpleName}> entities) {
                        return cuspid${entityClassSimpleName}Repository.saveAll(entities);
                    }
                    
                    @Override
                    public ${entityClassSimpleName} updateEntity(${entityClassSimpleName} entity) {
                        return cuspid${entityClassSimpleName}Repository.save(entity);
                    }
                    
                    @Override
                    public List<${entityClassSimpleName}> updateEntities(List<${entityClassSimpleName}> entities) {
                        return cuspid${entityClassSimpleName}Repository.saveAll(entities);
                    }
                    
                    @Override
                    public ${entityClassSimpleName} deleteEntityById(${entityIdClassSimpleName} id) {
                        ${entityClassSimpleName} entity = cuspid${entityClassSimpleName}Repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Could not find entity with id " + id));
                        cuspid${entityClassSimpleName}Repository.deleteById(id);
                        return entity;
                    }
                    
                    @Override
                    public List<${entityClassSimpleName}> deleteEntitiesByIds(List<${entityIdClassSimpleName}> ids) {
                        List<${entityClassSimpleName}> entities = cuspid${entityClassSimpleName}Repository.findAllById(ids);
                        cuspid${entityClassSimpleName}Repository.deleteAllById(ids);
                        return entities;
                    }
                    
                }
                """;

        API_TEMPLATE = """
                """;

        CONTROLLER_TEMPLATE = """
                """;
    }

    private Template() {
    }
}
