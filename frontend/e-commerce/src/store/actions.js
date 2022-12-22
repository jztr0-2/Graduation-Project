import { SET_CART_ITEM, ADD_CART_ITEM, REMOVE_CART_ITEM } from './constants';

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
