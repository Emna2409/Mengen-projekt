package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An in-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetInPlace<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        while (head != null && !pred.test(head.key)) {
            head = head.next;
        }
        ListItem<T> s = head;
        while (s != null && s.next != null ) {
            if (pred.test(s.next.key)) {
                s = s.next;
            } else {
                s.next = s.next.next;
            }
        }
        return this;

    }


    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        Comparator<? super ListItem<T>>  COMPARE =(s1, s2) -> {
            int k = cmp.compare(s1.key, s2.key);
            if (k == 0) {
                return cmp.compare(s1.next.key, s2.next.key);
            } else {
                return k;
            }
        };


        MySetInPlace<ListItem<T>> newlist = new MySetInPlace<>(null, COMPARE);
        ListItem<ListItem<T>> size = null;

        for (ListItem<T> M = head; M != null; M = M.next) {
            for (ListItem<T> N = other.head; N != null; N = N.next) {
                ListItem<T> k = new ListItem<>(M.key);
                k.next = new ListItem<>(N.key);
                ListItem<ListItem<T>> lista = new ListItem<>(k);


                if (newlist.head == null) {
                    newlist.head = lista;
                } else {
                    size.next = lista;
                }
                size = lista;
            }


        }

        return newlist;
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {

        ListItem<T> M = head;
        ListItem<T> kbal = null;


        while ( M != null) {
            boolean mawjouda = false;
            for (ListItem<T> N = other.head; N != null; N = N.next) {
                if (cmp.compare(M.key, N.key)==0) {
                    mawjouda = true;
                    break;

                }
            }
            if (mawjouda) {
                if (kbal == null) {
                    head = M.next;
                } else {
                    kbal.next = M.next;
                }
            } else{
                kbal=M;
            }
            M=M.next;

        }
        return this;


    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        ListItem<T> M1 = head;
        ListItem<T> kbal = null;

        if (heads.key==null || head.next==null || head.key==null){
            return new MySetAsCopy<>(null, cmp);
        }



        while (M1 != null) {
            boolean premier = true;
            for (ListItem<ListItem<T>> M2 = heads; M2 != null; M2 = M2.next) {
                boolean deuxieme = false;
                for (ListItem<T> M3 = M2.key; M3 != null; M3 = M3.next) {
                    if (cmp.compare(M1.key, M3.key) == 0) {
                        deuxieme = true;
                        break;
                    }
                }
                if (!deuxieme) {
                    premier = false;
                    break;
                }
            }

            if (!premier) {
                if (kbal== null) {
                    head = M1.next;
                } else {
                    kbal.next = M1.next;
                }
            } else {
               kbal = M1;
            }

            M1 =M1.next;
        }

        return this;
    }


}
