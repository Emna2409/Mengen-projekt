package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An out-of-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetAsCopy<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetAsCopy(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        MySetAsCopy<T> list=new MySetAsCopy<>(null,cmp);
        ListItem<T> size = null;
        for(ListItem<T> p = head; p != null; p = p.next){
            if (pred.test(p.key)  ){
                ListItem<T> newItem = new ListItem<>(p.key);
                if (list.head== null) {
                    list.head = newItem;
                } else {
                    size.next = newItem;

                }
                size = newItem;


            }
        }
        return list;

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



        MySetAsCopy <ListItem<T>>  newlist = new MySetAsCopy <>(null,COMPARE);
        ListItem<ListItem<T>> size = null;

        for(ListItem<T> M=head;M!=null;M=M.next) {
            for (ListItem<T> N = other.head; N != null; N = N.next) {
                ListItem<T> newone = new ListItem<>(M.key);
                newone.next = new ListItem<>(N.key);
                ListItem<ListItem<T>> k = new ListItem<>(newone);
                if (newlist.head == null) {
                    newlist.head = k;
                } else {
                    size.next = k;

                }
                size = k;
            }

        }
        return newlist;

    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        MySetAsCopy<T> listajdida = new MySetAsCopy<>(null, cmp);
        ListItem<T> size = null;

        for (ListItem<T> M = head; M != null; M = M.next) {
            boolean premier = false;
            for (ListItem<T> N = other.head; N != null; N = N.next) {
                if (cmp.compare(M.key, N.key)==0) {
                    premier = true;
                    break;
                }
            }
            if (!premier) {
                ListItem<T> MCopy = new ListItem<>(M.key);
                if (listajdida.head == null) {
                    listajdida.head = MCopy;
                } else {
                    size.next = MCopy;
                }
                size = MCopy;
            }
        }
        return listajdida;




    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        if (heads.key==null || head.next==null|| head.key==null ){
            return new MySetAsCopy<>(null, cmp);
        }

        MySetAsCopy<T> newnew = new MySetAsCopy<>(null, cmp);

        for (ListItem<T> M1 = heads.key; M1 != null; M1 = M1.next) {
            boolean premier = true;
            for (ListItem<ListItem<T>> M2 = heads.next; M2 != null; M2 = M2.next) {
                boolean deuxieme = false;
                for (ListItem<T> M3 = M2.key; M3 != null; M3 = M3.next) {
                    if (cmp.compare(M1.key, M3.key) == 0) {
                        deuxieme = true;
                        break;
                    }
                }
                if (!deuxieme) {
                    premier = false;
                }


            }
            if (premier) {
                ListItem<T> newlist = new ListItem<>(M1.key);
                if (newnew.head == null) {
                    newnew.head = newlist;
                } else {
                    ListItem<T> last = newnew.head;
                    while (last.next != null) {
                        last = last.next;
                    }
                    last.next = newlist;

                }

            }
        }

        return newnew;




    }
}
