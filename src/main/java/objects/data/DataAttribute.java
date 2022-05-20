package objects.data;

import lombok.Getter;
import lombok.Setter;

/**Абстрактный и «шаблонный» класс, который позволяет избавиться от «назначения» примитивных переменных */
@Getter @Setter
public class DataAttribute<T> {
    private T value;

    public DataAttribute(T value) {  this.value = value;  }

}
