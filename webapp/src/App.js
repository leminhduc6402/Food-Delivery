import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./layout/Header";
import Footer from "./layout/Footer";
import Home from "./components/Home";
import "bootstrap/dist/css/bootstrap.min.css";
import "react-toastify/dist/ReactToastify.css";
import Restaurant from "./components/Restaurant";
import Food from "./components/Food";
import Register from "./components/Register";
import Login from "./components/Login";
import { createContext, useReducer } from "react";
import cookie from "react-cookies";
import MyUserReducer from "./reducer/MyUserReducer";
import RestaurantOwn from "./components/RestaurantOwn";
import DetailFood from "./components/DetailFood";
import AddNewFood from "./components/AddNewFood";
import { ToastContainer } from "react-toastify";
import MyCartCounterReducer from "./reducer/MyCartCounterReducer";
import Cart from "./components/Cart";
import Profile from "./components/Profile";
import Stats from "./components/Stats";

export const MyUserContext = createContext();
export const MyCartContext = createContext();

const countCart = () => {
  let cart = JSON.parse(localStorage.getItem("cart") || null);
  if (cart !== null)
    return Object.values(cart).reduce((init, current) => init + current["quantity"], 0);
  return 0;
}

const App = () => {
  const [user, dispatch] = useReducer(
    MyUserReducer,
    cookie.load("user") || null
  );
  const [cartCounter, cartDispatch] = useReducer(MyCartCounterReducer, countCart());

  return (
    <>
      <MyUserContext.Provider value={[user, dispatch]}>
        <MyCartContext.Provider value={[cartCounter, cartDispatch]}>
          <BrowserRouter>
            <Header />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/restaurants/:id" element={<Restaurant />} />
              <Route path="/profile/:id" element={<Profile />} />
              <Route path="/foods/:id" element={<Food />} />
              <Route path="/restaurantOwn/:id" element={<RestaurantOwn />} />
              <Route path="/restaurantOwn/foods/:id" element={<DetailFood />} />
              <Route
                path="/restaurantOwn/:id/addFood/"
                element={<AddNewFood />}
              />
              <Route path="/cart" element={<Cart />} />
              <Route path="/restaurantOwn/stats/:id" element={<Stats />} />
            </Routes>
            <Footer />
          </BrowserRouter>
        </MyCartContext.Provider>
      </MyUserContext.Provider>
      <ToastContainer
        position="top-right"
        autoClose={2000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
      />
    </>
  );
};

export default App;
