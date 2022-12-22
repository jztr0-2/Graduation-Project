import {
    SET_CART_ITEM,
    ADD_CART_ITEM,
    REMOVE_CART_ITEM,
    REMOVE_ALL_CART_ITEMS,
    SET_TOAST_MESSAGE,
} from './constants';

export const setCartItem = (payload) => ({
    type: SET_CART_ITEM,
    payload,
});

export const addCartItem = (payload) => ({
    type: ADD_CART_ITEM,
    payload,
});

export const removeCartItem = (payload) => ({
    type: REMOVE_CART_ITEM,
    payload,
});

export const removeAllCartItems = (payload = '') => ({
    type: REMOVE_ALL_CART_ITEMS,
    payload,
});

export const setToastMessage = (payload) => ({
    type: SET_TOAST_MESSAGE,
    payload,
});
