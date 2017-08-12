package pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENCO7 on 8/11/2017.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GenericGenerator(name = "uuid_generator", strategy = "uuid")
    @GeneratedValue(generator = "uuid_generator")
    private String id;
    private String processNumber;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "o_id")
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    private String deliveryAddress;

    public Order() {
    }

    public Order(String processNumber) {
        this.processNumber = processNumber;
    }

    public Order(String processNumber, List<OrderItem> orderItemList) {
        this.processNumber = processNumber;
        this.orderItemList = orderItemList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
