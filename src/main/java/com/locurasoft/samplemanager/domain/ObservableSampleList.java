package com.locurasoft.samplemanager.domain;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ObservableSampleList implements ObservableList<Sample> {

    private final ObservableList<Sample> list;

    public ObservableSampleList() {
        list = FXCollections.observableArrayList();
    }

    @Override
    public void addListener(ListChangeListener<? super Sample> listener) {
        list.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super Sample> listener) {
        list.removeListener(listener);
    }

    @Override
    public boolean addAll(Sample... elements) {
        return list.addAll(elements);
    }

    @Override
    public boolean setAll(Sample... elements) {
        return list.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends Sample> col) {
        return list.setAll(col);
    }

    @Override
    public boolean removeAll(Sample... elements) {
        return list.removeAll(elements);
    }

    @Override
    public boolean retainAll(Sample... elements) {
        return list.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        list.remove(from, to);
    }

    @Override
    public FilteredList<Sample> filtered(Predicate<Sample> predicate) {
        return list.filtered(predicate);
    }

    @Override
    public SortedList<Sample> sorted(Comparator<Sample> comparator) {
        return list.sorted(comparator);
    }

    @Override
    public SortedList<Sample> sorted() {
        return list.sorted();
    }

    public void clearAndAddAll(List<Sample> samples) {
        list.clear();
        list.addAll(samples);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Sample> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Sample sample) {
        return list.add(sample);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Sample> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Sample> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<Sample> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Sample> c) {
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public Sample get(int index) {
        return list.get(index);
    }

    @Override
    public Sample set(int index, Sample element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Sample element) {
        list.add(index, element);
    }

    @Override
    public Sample remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Sample> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Sample> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Sample> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<Sample> spliterator() {
        return list.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super Sample> filter) {
        return list.removeIf(filter);
    }

    @Override
    public Stream<Sample> stream() {
        return list.stream();
    }

    @Override
    public Stream<Sample> parallelStream() {
        return list.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Sample> action) {
        list.forEach(action);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        list.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        list.removeListener(listener);
    }
}
