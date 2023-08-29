package coin.banggeul.property.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptionValue is a Querydsl query type for OptionValue
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOptionValue extends BeanPath<OptionValue> {

    private static final long serialVersionUID = -768735897L;

    public static final QOptionValue optionValue = new QOptionValue("optionValue");

    public final EnumPath<coin.banggeul.common.YesNo> airConditioner = createEnum("airConditioner", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> bed = createEnum("bed", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> bookshelf = createEnum("bookshelf", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> closet = createEnum("closet", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> desk = createEnum("desk", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> fridge = createEnum("fridge", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> gasStove = createEnum("gasStove", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> induction = createEnum("induction", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> laundry = createEnum("laundry", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> microwave = createEnum("microwave", coin.banggeul.common.YesNo.class);

    public final EnumPath<coin.banggeul.common.YesNo> sink = createEnum("sink", coin.banggeul.common.YesNo.class);

    public QOptionValue(String variable) {
        super(OptionValue.class, forVariable(variable));
    }

    public QOptionValue(Path<? extends OptionValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptionValue(PathMetadata metadata) {
        super(OptionValue.class, metadata);
    }

}

