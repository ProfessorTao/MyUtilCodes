package individual.professortao.utils

import DateUtil._

object DateUtilTester {
    def test_function(): Unit = {
        val test_date_str = "2017-02-21"
        val convert_to_date = date_str_to_date(test_date_str)
        val convert_to_calendar = date_str_to_calendar(test_date_str)
        val convert_to_date_str_from_date = date_to_date_str(convert_to_date)
        val convert_to_date_str_from_calendar = calendar_to_date_str(convert_to_calendar)
        printf("test_date_str: %s\n", test_date_str)
        printf("convert_to_date: %s\n", convert_to_date)
        printf("convert_to_calendar: %s\n", convert_to_calendar)
        printf("convert_to_date_str_from_date: %s\n", convert_to_date_str_from_date)
        printf("convert_to_date_str_from_calendar: %s\n", convert_to_date_str_from_calendar)

        val current = get_current_unix_timestamp();
        val current_date_str = get_date_str_from_unix_timestamp(current);
        printf("Current timestamp: %d\n", current)
        printf("Current date_str: %s\n", current_date_str)

        val the_timestamp = date_str_to_unix_timestamp(test_date_str)
        printf("Timestamp of %s is %d\n", test_date_str, the_timestamp)

        printf("Test get_date_str_after_today: %s\n", get_date_str_after_today(-1))
        printf("Test get_date_str_after_date_str: %s\n", get_date_str_after_date_str("2017-02-01", 4))

        val date_str_list = get_date_str_list_between_date_str("2017-02-04", "2017-02-10")
        var print_str = "Test get_date_str_list_between_date_str:"
        for (i <- date_str_list) {
            print_str = String.format("%s %s", print_str, i)
        }
        println(print_str)
    }

    def test_intstr_function(): Unit = {
        val test_date_str = "20170221"
        val convert_to_date = date_intstr_to_date(test_date_str)
        val convert_to_calendar = date_intstr_to_calendar(test_date_str)
        val convert_to_date_str_from_date = date_to_date_intstr(convert_to_date)
        val convert_to_date_str_from_calendar = calendar_to_date_intstr(convert_to_calendar)
        printf("test_date_str: %s\n", test_date_str)
        printf("convert_to_date: %s\n", convert_to_date)
        printf("convert_to_calendar: %s\n", convert_to_calendar)
        printf("convert_to_date_str_from_date: %s\n", convert_to_date_str_from_date)
        printf("convert_to_date_str_from_calendar: %s\n", convert_to_date_str_from_calendar)

        val current = get_current_unix_timestamp();
        val current_date_str = get_date_intstr_from_unix_timestamp(current);
        printf("Current timestamp: %d\n", current)
        printf("Current date_str: %s\n", current_date_str)

        val the_timestamp = date_intstr_to_unix_timestamp(test_date_str)
        printf("Timestamp of %s is %d\n", test_date_str, the_timestamp)

        printf("Test get_date_str_after_today: %s\n", get_date_intstr_after_today(-3))
        printf("Test get_date_str_after_date_str: %s\n", get_date_intstr_after_date_str("20170201", 4))

        val date_str_list = get_date_intstr_list_between_date_intstr("20170204", "20170210")
        var print_str = "Test get_date_str_list_between_date_str:"
        for (i <- date_str_list) {
            print_str = String.format("%s %s", print_str, i)
        }
        println(print_str)
    }

    def main(args: Array[String]): Unit = {
        println("  -- (1):")
        test_function()
        println("\n  -- (2):")
        test_intstr_function()
    }
}
