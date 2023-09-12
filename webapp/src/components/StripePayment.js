import React, { useContext, useState } from "react";
import axios from "axios";
import { loadStripe } from "@stripe/stripe-js";
import {
  Elements,
  CardElement,
  useStripe,
  useElements,
} from "@stripe/react-stripe-js";
import { Button } from "react-bootstrap";
import { authApi, endpoints } from "../configs/Apis";
import { toast } from "react-toastify";
import { MyCartContext, MyUserContext } from "../App";
import { useNavigate } from "react-router-dom";

// const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLIC_KEY);
const stripePromise = loadStripe(
  "pk_test_51Nop6qCPBg1kGRFKRteXXO1NNvDzb9SMCMLUgjP31qtsoljawSjEZQloSbpPuS45f620wqRMl91DpMnxT5yX297j00ZVK8FirE"
);

const CheckoutForm = () => {
  const CARD_ELEMENT_OPTIONS = {
    style: {
      base: {
        color: "#000",
        fontSize: "16px",
        fontFamily: "sans-serif",
        fontSmoothing: "antialiased",

        "::placeholder": {
          color: "#000",
        },
      },
      invalid: {
        color: "#e5424d",
        ":focus": {
          color: "#303238",
        },
      },
    },
  };

  const stripe = useStripe();
  const elements = useElements();
  const [user, dispatch] = useContext(MyUserContext);
  const nav = useNavigate();
  const [carts, setCarts] = useState(
    localStorage.getItem("cart")
      ? JSON.parse(localStorage.getItem("cart"))
      : null
  );
  const [, cartDispatch] = useContext(MyCartContext);
  
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (user === null) {
      toast.error("Vui lòng đăng nhập để thanh toán");
      nav("/login");
    } else {
      if (!stripe || !elements) return;

      const card = elements.getElement(CardElement);
      const result = await stripe.createToken(card);

      if (result.error) {
        console.error(result.error.message);
      } else {
        authApi()
          .post(endpoints.stripe, {
            token: result.token.id,
            amount: 500, // for example 500 cents ($5.00)
          })
          .then((response) => {
            if (response.data) {
              toast.success("Thanh toán thành công")
              localStorage.removeItem("cart");

              cartDispatch({
                type: "update",
                payload: 0,
              });

              setCarts([]);
              nav("/");
            } else {
              toast.error("Thanh toán thất bại")
            }
          })
          .catch((error) => console.error("Error:", error));
      }
    }
  };

  return (
    <form
      style={{
        width: "500px",
        background: "#ccc",
        padding: "20px 20px",
        borderRadius: "6px",
      }}
      onSubmit={handleSubmit}
    >
      <CardElement options={CARD_ELEMENT_OPTIONS} />
      <Button
        style={{
          marginTop: "12px",
          width: "100%",
        }}
        type="submit"
        disabled={!stripe}
      >
        Pay
      </Button>
    </form>
  );
};

const StripePayment = () => {
  return (
    <Elements stripe={stripePromise}>
      <CheckoutForm />
    </Elements>
  );
};

export default StripePayment;
