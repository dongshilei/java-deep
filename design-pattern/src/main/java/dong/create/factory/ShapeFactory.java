package dong.create.factory;

/**
 * @author DONGSHILEI
 * @create 2019-08-23 11:20
 * @since 1.0
 */
public class ShapeFactory {
    public static final String SQUARE = "SQUARE";
    public static final String RECTANGLE = "RECTANGLE";
    public static final String CIRCLE = "CIRCLE";

    public Shape getShape(String shape){
        if (shape==null||"".equals(shape)){
            return null;
        }
        if (SQUARE.equals(shape)){
            return new Square();
        } else if (RECTANGLE.equals(shape)){
            return new Rectangle();
        } else if (CIRCLE.equals(shape)){
            return new Circle();
        }
        return null;
    }
}
