package ru.ncs.DemoShop.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderedId.class)
@Table(name = "ordered_product")
public class OrderedProduct implements Serializable {
   @Id
   @Column(name = "order_id")
   private UUID orderId;

   @Id
   @Column(name = "product_id")
   private UUID productId;

   @Column(name = "quantity")
   private int quantity;

   @ManyToOne
   @JoinColumn(name = "order_id", insertable=false, updatable=false)
   private Order ownerOrder;

   @ManyToOne
   @JoinColumn(name = "product_id", insertable=false, updatable=false)
   private Product ownerProduct;


}
