package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOthers is a Querydsl query type for Others
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOthers extends BeanPath<Others> {

    private static final long serialVersionUID = -2131115752L;

    public static final QOthers others = new QOthers("others");

    public final EnumPath<coin.banggeul.common.YesNo> elevator = createEnum("elevator", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> loan = createEnum("loan", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> parking = createEnum("parking", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> pet = createEnum("pet", coin.banggeul.common.YesNo.class);

    public QOthers(String variable) {
        super(Others.class, forVariable(variable));
    }

    public QOthers(Path<? extends Others> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOthers(PathMetadata metadata) {
        super(Others.class, metadata);
    }

}

