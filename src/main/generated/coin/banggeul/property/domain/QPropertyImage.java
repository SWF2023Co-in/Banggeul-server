package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPropertyImage is a Querydsl query type for PropertyImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPropertyImage extends EntityPathBase<PropertyImage> {

    private static final long serialVersionUID = -251005647L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPropertyImage propertyImage = new QPropertyImage("propertyImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> index = createNumber("index", Long.class);

    public final StringPath originalName = createString("originalName");

    public final QProperty property;

    public final StringPath url = createString("url");

    public QPropertyImage(String variable) {
        this(PropertyImage.class, forVariable(variable), INITS);
    }

    public QPropertyImage(Path<? extends PropertyImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPropertyImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPropertyImage(PathMetadata metadata, PathInits inits) {
        this(PropertyImage.class, metadata, inits);
    }

    public QPropertyImage(Class<? extends PropertyImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property"), inits.get("property")) : null;
    }

}

