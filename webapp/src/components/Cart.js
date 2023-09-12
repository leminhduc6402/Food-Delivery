import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useContext, useState } from "react";
import { Alert, Button, Container, Form, Modal, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { MyCartContext, MyUserContext } from "../App";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { authApi, endpoints } from "../configs/Apis";
import StripePayment from "./StripePayment";

const Cart = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [, cartDispatch] = useContext(MyCartContext);
  const [carts, setCarts] = useState(
    localStorage.getItem("cart")
      ? JSON.parse(localStorage.getItem("cart"))
      : null
  );
  const nav = useNavigate();
  const [paymentMethod, setPaymentMethod] = useState("COD");
  const [show, setShow] = useState(false);

  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);

  const deleteItem = (item) => {
    cartDispatch({
      type: "dec",
      payload: item.quantity,
    });

    if (item.id in carts) {
      setCarts((current) => {
        delete current[item.id];
        localStorage.setItem("cart", JSON.stringify(current));
        return current;
      });
    }
  };

  const updateItem = () => {
    localStorage.setItem("cart", JSON.stringify(carts));
    cartDispatch({
      type: "update",
      payload: Object.values(carts).reduce(
        (init, current) => init + current["quantity"],
        0
      ),
    });
  };
  const handlePay = () => {
    if (user === null) {
      toast.error("Vui lòng đăng nhập để thanh toán");
      nav("/login?next=/cart");
    } else {
      if (Object.values(carts)[0].paymentMethod || Object.values(carts)[0].paymentMethod === '-1' || Object.values(carts)[0].paymentMethod === '--- Chọn phương thức thanh toán ---') {
        const process = async () => {
          let res = await authApi().post(endpoints.pay, carts);
          if (res.status === 200) {
            localStorage.removeItem("cart");

            cartDispatch({
              type: "update",
              payload: 0,
            });

            setCarts([]);
          }
        };

        process();
        toast.success("Thanh toán thành công");
        nav(-1);
      } else {
        toast.error("Vui lòng chọn phương thức thanh toán");
      }
    }
  };

  let restaurantData;
  const groupOrderByRestaurant = {};
  if (carts !== null) {
    Object.values(carts).forEach((item) => {
      const restaurantId = item.restaurantId.id;
      if (!groupOrderByRestaurant[restaurantId]) {
        groupOrderByRestaurant[restaurantId] = [];
      }
      groupOrderByRestaurant[restaurantId].push(item);
    });

    restaurantData = Object.keys(groupOrderByRestaurant).map((restaurantId) => {
      const items = groupOrderByRestaurant[restaurantId];
      const subtotal = items.reduce(
        (total, item) => total + item.price * item.quantity,
        0
      );
      const shippingFee = items[0].restaurantId.deliveryFee;

      return {
        restaurantId,
        items,
        subtotal,
        shippingFee,
        totalAmount: subtotal + shippingFee,
      };
    });
  }

  const updatePaymentMethod = (method) => {
    if (carts) {
      const updatedCarts = Object.values(carts).map((item) => ({
        ...item,
        paymentMethod: method,
      }));
      setSelectedPaymentMethod(method);
      setCarts(updatedCarts);

      // Lưu trữ vào localStorage
      localStorage.setItem("cart", JSON.stringify(updatedCarts));
    }
  };

  if (carts === null) {
    return (
      <Alert variant="danger" className="my-5 text-center">
        Chưa có sản phẩm nào trong giỏ
      </Alert>
    );
  }
  return (
    <>
      <Container>
        <h1 className="mt-3 text-center">GIỎ HÀNG CỦA BẠN</h1>

        {restaurantData.map((restaurant) => (
          <div key={restaurant.restaurantId}>
            <Table striped bordered hover>
              <colgroup>
                <col style={{ width: "20%" }} />{" "}
                <col style={{ width: "20%" }} />{" "}
                <col style={{ width: "20%" }} />{" "}
                <col style={{ width: "20%" }} />{" "}
                <col style={{ width: "20%" }} />{" "}
              </colgroup>
              <thead>
                <tr>
                  <th>Mã sản phẩm</th>
                  <th>Tên sản phẩm</th>
                  <th>Cửa hàng</th>
                  <th>Giá</th>
                  <th>Số lượng</th>
                </tr>
              </thead>
              <tbody>
                {restaurant.items.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.name}</td>
                    <td>{c.restaurantId.name}</td>
                    <td>{c.price}</td>
                    <td>
                      <Form.Control
                        type="number"
                        value={c.quantity}
                        onBlur={updateItem}
                        onChange={(e) =>
                          setCarts({
                            ...carts,
                            [c.id]: {
                              ...carts[c.id],
                              quantity: parseInt(e.target.value),
                            },
                          })
                        }
                      />
                    </td>
                    <td
                      style={{ textAlign: "center", verticalAlign: "middle" }}
                    >
                      <Button
                        variant=""
                        style={{ outline: "none" }}
                        onClick={() => deleteItem(c)}
                      >
                        <FontAwesomeIcon
                          className="text-danger"
                          icon={faTrashCan}
                        />
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
            <div>Tiền đơn hàng: {restaurant.subtotal}</div>
            <div>Tiền vận chuyển: {restaurant.shippingFee}</div>
            <div>Tổng tiền: {restaurant.totalAmount}</div>
          </div>
        ))}

        <Form.Control
          className="my-3"
          as="select"
          onChange={(e) => updatePaymentMethod(e.target.value)}
        >
          <option value="-1">--- Chọn phương thức thanh toán ---</option>
          <option value="COD">Thanh toán khi nhận hàng</option>
          <option value="Stripe">Stripe</option>
        </Form.Control>
        {selectedPaymentMethod === "Stripe" && <StripePayment />}

        {selectedPaymentMethod !== "Stripe" && (
          <Button onClick={handlePay}>Thanh toán</Button>
        )}
        {/* <StripePayment />
        <Button onClick={handlePay}>Thanh toán</Button> */}
      </Container>
    </>
  );
};

export default Cart;
