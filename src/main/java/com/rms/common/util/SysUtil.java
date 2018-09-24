package com.rms.common.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SysUtil {
    public static <T, U extends Comparable<? super U>> void sortAsc(List<T> list, Function<? super T, ? extends U> keyExtractor) {
        list.sort(comparingAsc(keyExtractor));
    }

    public static <T, U extends Comparable<? super U>, E extends Comparable<? super E>> void sortAsc(List<T> list, Function<? super T, ? extends U> keyExtractor, Function<? super T, ? extends E> keyExtractorOther) {
        list.sort(comparingAsc(keyExtractor, keyExtractorOther));
    }
    public static <T, U extends Comparable<? super U>> void sortDesc(List<T> list, Function<? super T, ? extends U> keyExtractor) {
        list.sort(comparingDesc(keyExtractor));
    }

    public static <T, U extends Comparable<? super U>, E extends Comparable<? super E>> void sortDesc(List<T> list, Function<? super T, ? extends U> keyExtractor, Function<? super T, ? extends E> keyExtractorOther) {
        list.sort(comparingDesc(keyExtractor, keyExtractorOther));
    }
    private static <T, U extends Comparable<? super U>> Comparator<T> comparingAsc(
            Function<? super T, ? extends U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
    }
    private static <T, U extends Comparable<? super U>, E extends Comparable<? super E>> Comparator<T> comparingAsc(
            Function<? super T, ? extends U> keyExtractor, Function<? super T, ? extends E> keyExtractorOther) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) (c1, c2) -> {
            int res = keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
            return (res != 0) ? res : keyExtractorOther.apply(c1).compareTo(keyExtractorOther.apply(c2));
        };
    }
    private static <T, U extends Comparable<? super U>> Comparator<T> comparingDesc(
            Function<? super T, ? extends U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> keyExtractor.apply(c2).compareTo(keyExtractor.apply(c1));
    }
    private static <T, U extends Comparable<? super U>, E extends Comparable<? super E>> Comparator<T> comparingDesc(
            Function<? super T, ? extends U> keyExtractor, Function<? super T, ? extends E> keyExtractorOther) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) (c1, c2) -> {
            int res = keyExtractor.apply(c2).compareTo(keyExtractor.apply(c1));
            return (res != 0) ? res : keyExtractorOther.apply(c2).compareTo(keyExtractorOther.apply(c1));
        };
    }
    public static String dateFormat(Date date) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateString = sdf.format(date);
        return dateString;
    }
    public static String dateFormat(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateString = sdf.format(date);
        return dateString;
    }
}
