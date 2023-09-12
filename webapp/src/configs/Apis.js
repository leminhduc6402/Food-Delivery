import axios from "axios";
import cookie from "react-cookies";

const SERVER_CONTEXT = "/Nomin";
const SERVER = "http://localhost:8080";

export const endpoints = {
    "provinces": "https://vapi.vnappmob.com/api/province/",
    "districts": "https://vapi.vnappmob.com/api/province/district/",
    "wards": "https://vapi.vnappmob.com/api/province/ward/",
    "login": `${SERVER_CONTEXT}/api/login/`, 
    "getCurrentUser": `${SERVER_CONTEXT}/api/getCurrentUser/`,
    "foods": `${SERVER_CONTEXT}/api/foods/`,
    "follow": `${SERVER_CONTEXT}/api/follows/`,
    "restaurants": `${SERVER_CONTEXT}/api/restaurants/`,
    "pay": `${SERVER_CONTEXT}/api/pay/`,
    "comments": `${SERVER_CONTEXT}/api/comments/`,    
    "getFoodById": `${SERVER_CONTEXT}/api/foods/food/`,
    "register": `${SERVER_CONTEXT}/api/users/`,
    "getRestaurantByUserId": `${SERVER_CONTEXT}/api/restaurantOwn/`,
    "updateFood":(id) => `${SERVER_CONTEXT}/api/foods/${id}/updateFood`,
    "getOrderDetailByUserId": `${SERVER_CONTEXT}/api/orders/user/`,
    "getOrderDetailByRestaurantId": `${SERVER_CONTEXT}/api/orders/restaurant/`,
    "stripe": `${SERVER_CONTEXT}/api/stripe/`,
}

export const authApi = () => {
    // const token = cookie.load("token").trim();
    return axios.create({
        baseURL: SERVER,        
        headers: {
            Authorization: cookie.load("token")
        }
    })
}


export default axios.create({
    baseURL: SERVER,
});