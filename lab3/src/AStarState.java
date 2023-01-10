/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A* для вычисления пути по карте.
 * Это состояние включает набор «открытых путевых точек» и другой набор «закрытых путевых точек».
 * Кроме того, этот класс предоставляет основные операции, необходимые алгоритму поиска пути A* для выполнения своей обработки.
 **/
import java.util.HashMap;
import java.util.Map;
public class AStarState
{
    /** Это ссылка на карту, по которой перемещается алгоритм A*. **/
    private Map2D map;
    //инициализировали два поля класса AStarState хэш-карты - открытых и закрытых полей.
    private Map<Location, Waypoint> openWaypoints;
    private Map<Location, Waypoint> closedWaypoints;

    /** Инициализировать новый объект состояния для использования алгоритмом поиска пути A*. **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        openWaypoints = new HashMap<Location, Waypoint>();
        closedWaypoints = new HashMap<Location, Waypoint>();
    }

    /** Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку с минимальной общей стоимостью.
     * Если открытых путевых точек нет, этот метод возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()//в нём программа перебирает все значения
    // openWaypoints и находит среди них минимум по общей стоимости waypoint-а.
    {
        Waypoint minWaypoint = null;
        Waypoint comparableWaypoint = null;
        float minCost = Float.MAX_VALUE;
        //Итерируем по всем открытым точкам и постепенно находим минимум.
        for (int i = 0; i < openWaypoints.size(); i++){
            comparableWaypoint = (Waypoint) openWaypoints.values().toArray()[i];//Метод values () возвращает массив, содержащий список констант перечисления.
            // toArray-приведение списков к массивам:
            if(comparableWaypoint.getTotalCost() < minCost){
                minWaypoint = comparableWaypoint;
                minCost = comparableWaypoint.getTotalCost();
            }
        }
        return minWaypoint;
    }

    /**
     * Этот метод добавляет путевую точку (или потенциально обновляет путевую точку, уже находящуюся в ней) в коллекции «открытых путевых точек».
     * Если в местоположении новой путевой точки еще нет открытой путевой точки, новая путевая точка просто добавляется в коллекцию.
     * Однако, если в местоположении новой путевой точки уже есть путевая точка, новая путевая точка заменяет старую только <em>
     * если</em> значение «предыдущая стоимость» новой путевой точки меньше, чем значение «предыдущая стоимость» текущей путевой точки.
     **/
    //Реализуем метод addOpenWaypoint, в котором добавляем waypoint в хэш-карту если его там нет или если
    // он там присутствует, но стоимость пути до новой вершины меньше текущей.
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //Пробуем найти точку в открытых точках.
        Waypoint toCompare = openWaypoints.get(newWP.getLocation());
        //Если точка не найдена или она присутсвует, но её пред. значение меньше то добавляем/заменяем ее в коллекции.
        if(toCompare == null || toCompare.getPreviousCost() > newWP.getPreviousCost()){
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }

    /** Возвращает текущее количество открытых путевых точек. **/
    //В данном методе мы просто возвращаем длину “хэш-карты” открытых полей - openWaypoints.
    public int numOpenWaypoints()
    {
        return openWaypoints.size();//В данном методе мы просто возвращаем длину “хэш-карты” открытых полей - openWaypoints.
    }

    /** Этот метод перемещает путевую точку в указанном месте из открытого списка в закрытый список. **/
    //Реализуем метод closeWaypoint, нам необходимо удалить waypoint из хэш-карты открытых точек
    // и переместить в хэш- карту закрытых точек.
    public void closeWaypoint(Location loc)
    {
        //Получаем точку по хэшу локации из открытых точек
        Waypoint point = openWaypoints.get(loc);
        //Если point - null, то этой точки нет в открытых, следовательно дальше нечего делать
        if(point == null) return;
        //Удаляем точку из списка открытых
        openWaypoints.remove(loc);
        //Добавляем точку в список закрытых
        closedWaypoints.put(loc, point);
    }

    /** Возвращает true, если коллекция закрытых путевых точек содержит путевую точку для указанного местоположения. **/
    // Реализуем метод isLocationClosed, нам необходимо проверить закрыта ли локация,
    //т. е. мы проверяем находится ли она в хэш-карте закрытых вершин
    public boolean isLocationClosed(Location loc)
    {
        return closedWaypoints.get(loc) != null;
    }
}