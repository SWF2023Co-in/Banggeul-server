package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDocumentRegistrationYn is a Querydsl query type for DocumentRegistrationYn
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDocumentRegistrationYn extends BeanPath<DocumentRegistrationYn> {

    private static final long serialVersionUID = 1668222814L;

    public static final QDocumentRegistrationYn documentRegistrationYn = new QDocumentRegistrationYn("documentRegistrationYn");

    public final EnumPath<coin.banggeul.common.YesNo> buildingLedgerYn = createEnum("buildingLedgerYn", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> registryInfoYn = createEnum("registryInfoYn", coin.banggeul.common.YesNo.class);

    public QDocumentRegistrationYn(String variable) {
        super(DocumentRegistrationYn.class, forVariable(variable));
    }

    public QDocumentRegistrationYn(Path<? extends DocumentRegistrationYn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDocumentRegistrationYn(PathMetadata metadata) {
        super(DocumentRegistrationYn.class, metadata);
    }

}

