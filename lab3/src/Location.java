/**
 * Этот класс представляет определенное место на 2D-карте. Координаты являются целочисленными значениями.
 **/
public class Location
{
    /** Координата X этого местоположения. **/
    public int xCoord;

    /** Координата Y этого местоположения. **/
    public int yCoord;

    /** Создает новое местоположение с указанными целочисленными координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новую локацию с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    public boolean equals(Object obj){
        //мы принимаем Object (ссылку на объект) и внутри проверяем является ли объект экземпляром класса Location
        if (obj instanceof Location){
            Location loc = (Location) obj;
            //если является сравниваем координаты объекта
            return (loc.xCoord == xCoord && loc.yCoord == yCoord);
        }
        //если не является
        return false;
    }

    public int hashCode(){
        //Преобразование координат в уникальное значение.
        //Возвращаем хэш-код, сумму объекта по его координатам.
        return (xCoord * xCoord) + (yCoord * (xCoord - 7));
    }
}