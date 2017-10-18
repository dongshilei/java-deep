package dong.jodaTime;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Locale.CHINESE;

/**
 * Created by DONGSHILEI on 2017/7/11.
 */
public class JodaTimeTest {
    private static final String FORMATE_DATE = "yyyy-MM-dd";
    private static final String FORMATE_SECONDS = "HH:mm:ss";
    private static final String FORMATE_FULL = FORMATE_DATE.concat(" ").concat(FORMATE_SECONDS);

    /**
     * 初始化方法
     * 1、参的构造方法会创建一个在当前系统所在时区的当前时间，精确到毫秒 2017-03-15T12:31:33.517+08:00
     * 2、DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour)根据传入的时间构造
     * 3、DateTime(long instant) 这个构造方法创建出来的实例，是通过一个long类型的时间戳，它表示这个时间戳距1970-01-01T00:00:00Z的毫秒数。使用默认的时区
     * 4、DateTime(Object instant) 这个构造方法可以通过一个Object对象构造一个实例。这个Object对象可以是这些类型
     */
    public static void useCaseOne(){
        DateTime dt = new DateTime();
        DateTime dt1 = new DateTime(2017,8,20,18,54,0);
        DateTime dt2 = new DateTime(1487473917004L);
        DateTime dt3 = new DateTime(new Date());
        DateTime dt4 = new DateTime("2017-03-15T12:22:22");
        System.out.println(dt.toString("yyyy-MM-dd",CHINESE));
        //字符串转日期格式
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy年MM月");
        DateTime parse = DateTime.parse("2017年09月",format);
        System.out.println(parse.monthOfYear().get());
    }

    /**
     * 涉及时间计算
     */
    private static void useCaseWith(){

        DateTime dt = new DateTime();
        // 获取当前时间月的第一天
        LocalDate firstDayOfMonth = dt.toLocalDate().withDayOfMonth(1);

        // 获取当前周的周一和周末
        System.out.println(String.format("min:%s, max:%s",
                DateTime.now().dayOfWeek().withMinimumValue().toString("yyyy-MM-dd"),
                DateTime.now().dayOfWeek().withMaximumValue().toString("yyyy-MM-dd")));

        // 当前月的第一天和最后一天
        System.out.println(String.format("min:%s, max:%s",
                DateTime.now().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd"),
                DateTime.now().dayOfMonth().withMaximumValue().toString("yyyy-MM-dd")));

        // 当前年的第一天和最后一天
        System.out.println(String.format( "min:%s, max:%s",
                DateTime.now().dayOfYear().withMinimumValue().toString("yyyy-MM-dd"),
                DateTime.now().dayOfYear().withMaximumValue().toString("yyyy-MM-dd")));

        // 10天后的日期
        System.out.println(DateTime.now().dayOfYear().addToCopy(10).toString(FORMATE_DATE));
        System.out.println(DateTime.now().plusDays(10).toString(FORMATE_DATE));

        // 10天前的日期
        System.out.println("10天前的日期:"+DateTime.now().minusDays(10).toString(FORMATE_DATE));

        // 当前时间的10小时之前的时间
        System.out.println(DateTime.now().minusHours(10).toString(FORMATE_DATE));

        //取前几秒的时间
        System.out.println( dt.secondOfMinute().addToCopy(-3));


        DateTime begin = new DateTime(2017, 3, 1, 11, 13, 40);
        Duration duration = new Duration(begin, dt);

        // 两个时间之间 所差 天，小时 ，分，秒
        System.out.println( begin.toString(FORMATE_FULL) + " 与 " + dt.toString(FORMATE_FULL) + " 相差的天数：" + duration.getStandardDays());
        System.out.println(begin.toString(FORMATE_FULL) + " 与 "  + dt.toString(FORMATE_FULL) + " 相差的小时数：" + duration.getStandardHours());
        System.out.println(begin.toString(FORMATE_FULL) + " 与 "  + dt.toString(FORMATE_FULL) + " 相差的分钟：" + duration.getStandardMinutes());
        System.out.println(begin.toString(FORMATE_FULL) + " 与 "  + dt.toString(FORMATE_FULL) + " 相差的秒：" + duration.getStandardSeconds());
        //计算区间天数  ,小时，秒
        Period p = new Period(new DateTime(2017, 1, 1, 0, 0, 0),
                new DateTime(2017, 3, 30, 0, 0, 0), PeriodType.days());
        System.out.println("相隔的天："+p.getDays());


        // 计算两个日期相差几天 (同上面方法)
        System.out.println("计算两个日期相差几天:"+ Days.daysBetween(DateTime.parse("2017-03-16"), DateTime.parse("2017-04-02")).getDays());

        // 计算之前月份的时间操作
        LocalDate d = LocalDate.now();

        // 上个月(可以是之前的任意月)的最后一天
        LocalDate lastDayOfPreviousMonth = d.minusMonths(1).dayOfMonth().withMaximumValue();
        System.out.println("上个月的最后一天："+lastDayOfPreviousMonth.toString(FORMATE_DATE,Locale.CHINESE));

        LocalDate.Property e = d.minusWeeks(1).dayOfWeek();
        System.out.println("上周的周一：" + e.withMinimumValue().toString(FORMATE_DATE, Locale.CHINESE));
        System.out.println("上周的周日：" + e.withMaximumValue().toString(FORMATE_DATE, Locale.CHINESE));

        LocalDate.Property e2 = d.plusWeeks(1).dayOfWeek();
        System.out.println("下周的周一：" + e2.withMinimumValue().toString(FORMATE_DATE, Locale.CHINESE));
        System.out.println("下周的周日：" + e2.withMaximumValue().toString(FORMATE_DATE, Locale.CHINESE));

        //汉字形式标识今年和去年时间
        System.out.println("汉字形式：" + dt.year().getAsText(Locale.CHINESE));
        System.out.println("汉字形式：" + dt.monthOfYear().getAsText(Locale.CHINESE));
        System.out.println("汉字形式："+ dt.minusYears(1).dayOfMonth().getAsText(Locale.CHINESE));
        System.out.println("汉字形式：" + dt.dayOfWeek().getAsText(Locale.CHINESE));

        // 判断是否是闰年 闰月
        System.out.println("是否闰月:" + dt.monthOfYear().isLeap());
        System.out.println("是否闰年:" + dt.year().isLeap());
        System.out.println("去年是否闰年:" + dt.minusYears(1).year().isLeap());

        //取得一天的开始时间和结束时间
        System.out.println("取得一天的开始时间:"+dt.withTimeAtStartOfDay().toString(FORMATE_FULL));
        System.out.println("取得一天的结束时间:"+dt.millisOfDay().withMaximumValue().toString(FORMATE_FULL));

        //获取现在距离今天结束还有多久时间
        System.out.println("获取现在距离今天结束还有多久时间:"+(dt.millisOfDay().withMaximumValue().getMillis() - dt.getMillis()));

        //判断时间跨度是否包含当前时间,某个时间
        Interval interval = new Interval(new DateTime(2017, 1, 1, 0, 0, 0),new DateTime(2017, 8, 30, 0, 0, 0));
        System.out.println(interval.containsNow());
        boolean contained = interval.contains(new DateTime("2012-03-01"));


        //10 天后 那周的周一是
        System.out.println(dt.plusDays(10).dayOfWeek().withMinimumValue().toString(FORMATE_FULL));

        // DateTime与java.util.Date对象,当前系统TimeMillis转换
        DateTime temp1 = new DateTime(new Date());
        Date temp2 = dt.toDate();
        DateTime temp3 = new DateTime(System.currentTimeMillis());
        //使用Calendar构造DateTime
        Calendar calendar = Calendar.getInstance();
        DateTime  temp4 = new DateTime(calendar);
    }
    /**
     * 这个是get方法
     * 最大单位是年，最小单位毫秒
     * api中均可以最大单位下的子类
     * 比如：可以获取年终 月、日、时、分、秒、毫秒
     * 一下代码不全部列出
     */
    private static void useCaseGet(){
        DateTime dt = new DateTime();
        //获取当前时间的年
        int year = dt.getYear();
        //获取当前时间的月
        int month = dt.getMonthOfYear();
        //获取当前时间是一年中的第几天
        int dayOfYear = dt.getDayOfYear();
        //获取一个月中的天
        int day =  dt.getDayOfMonth();
        //获取一周中的周几
        int week = dt.getDayOfWeek();
        //一天中的第几小时(取整)
        int hour = dt.getHourOfDay();
        //获取星期年
        int weekOfyear =  dt.getWeekyear();
        //当前时间的秒中的毫秒
        int ms = dt.getMillisOfSecond();
        //获取当前时间的秒
        int second = dt.getSecondOfDay();
        //获取当前时间的毫秒
        long millis = dt.getMillis();

    }

    /**
     * 获取传入时间的日期的最后一毫秒
     * @param time
     * @return
     */
    public static long getDayLastCurrentTimeMillis(long time){
        DateTime dateTime = new DateTime(time);
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DateTime dt = new DateTime(year, month, day, 23, 59, 59, 999);
        return dt.getMillis();
    }

    /**
     * 获取传入时间的日期的第一毫秒
     * @param time
     * @return
     */
    public static long getDayFirstCurrentTimeMillis(long time){
        DateTime dateTime = new DateTime(time);
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DateTime dt = new DateTime(year, month, day, 0, 0, 0);
        return dt.getMillis();
    }
    public static void main(String[] args) {
        //useCaseOne();
        //useCaseWith();
        DateTime begin = new DateTime(1504172432622L);
        DateTime end = begin.plusDays(31);
        int months = Months.monthsBetween(begin, end).getMonths();
        System.out.println(months);
        DateTime end2 = begin.plusMonths(months);
        long endMillis = end.getMillis();
        long end2Millis = end2.getMillis();
        if(endMillis-end2Millis>0){
            months+=1;
        }
        System.out.println(months);

    }
}
