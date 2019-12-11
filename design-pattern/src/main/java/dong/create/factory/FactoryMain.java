package dong.create.factory;

/**
 * @author DONGSHILEI
 * @create 2019-08-23 11:19
 * @since 1.0
 */
public class FactoryMain {

    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();
        Shape square = factory.getShape(ShapeFactory.SQUARE);
        square.draw();
        Shape rectangle = factory.getShape(ShapeFactory.RECTANGLE);
        rectangle.draw();
        Shape circle = factory.getShape(ShapeFactory.CIRCLE);
        circle.draw();
    }
}
