package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProperty is a Querydsl query type for Property
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProperty extends EntityPathBase<Property> {

    private static final long serialVersionUID = 875143754L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProperty property = new QProperty("property");

    public final coin.banggeul.common.QBaseTimeEntity _super = new coin.banggeul.common.QBaseTimeEntity(this);

    public final QAddress address;

    public final NumberPath<Double> area = createNumber("area", Double.class);

    public final NumberPath<Long> buildingFloor = createNumber("buildingFloor", Long.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Double> deposit = createNumber("deposit", Double.class);

    public final EnumPath<NSEW> direction = createEnum("direction", NSEW.class);

    public final QDocumentRegistrationYn documentYn;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final coin.banggeul.member.domain.QMember landlord;

    public final NumberPath<Double> maintenanceFee = createNumber("maintenanceFee", Double.class);

    public final StringPath message = createString("message");

    public final DatePath<java.time.LocalDate> movingInDate = createDate("movingInDate", java.time.LocalDate.class);

    public final QOptionValue options;

    public final QOthers others;

    public final NumberPath<Double> rentalFee = createNumber("rentalFee", Double.class);

    public final EnumPath<RentalType> rentalType = createEnum("rentalType", RentalType.class);

    public final NumberPath<Long> roomFloor = createNumber("roomFloor", Long.class);

    public final EnumPath<RoomType> roomType = createEnum("roomType", RoomType.class);

    public final EnumPath<OS> structure = createEnum("structure", OS.class);

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public QProperty(String variable) {
        this(Property.class, forVariable(variable), INITS);
    }

    public QProperty(Path<? extends Property> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProperty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProperty(PathMetadata metadata, PathInits inits) {
        this(Property.class, metadata, inits);
    }

    public QProperty(Class<? extends Property> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.documentYn = inits.isInitialized("documentYn") ? new QDocumentRegistrationYn(forProperty("documentYn")) : null;
        this.landlord = inits.isInitialized("landlord") ? new coin.banggeul.member.domain.QMember(forProperty("landlord")) : null;
        this.options = inits.isInitialized("options") ? new QOptionValue(forProperty("options")) : null;
        this.others = inits.isInitialized("others") ? new QOthers(forProperty("others")) : null;
    }

}

