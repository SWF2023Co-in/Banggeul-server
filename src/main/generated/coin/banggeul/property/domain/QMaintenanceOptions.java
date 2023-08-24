package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaintenanceOptions is a Querydsl query type for MaintenanceOptions
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMaintenanceOptions extends BeanPath<MaintenanceOptions> {

    private static final long serialVersionUID = 1348179520L;

    public static final QMaintenanceOptions maintenanceOptions = new QMaintenanceOptions("maintenanceOptions");

    public final EnumPath<coin.banggeul.common.YesNo> electricity = createEnum("electricity", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> gas = createEnum("gas", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> internet = createEnum("internet", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> water = createEnum("water", coin.banggeul.common.YesNo.class);

    public QMaintenanceOptions(String variable) {
        super(MaintenanceOptions.class, forVariable(variable));
    }

    public QMaintenanceOptions(Path<? extends MaintenanceOptions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaintenanceOptions(PathMetadata metadata) {
        super(MaintenanceOptions.class, metadata);
    }

}

