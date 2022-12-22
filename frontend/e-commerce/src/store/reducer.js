import {
    SET_CART_ITEM,
    ADD_CART_ITEM,
    REMOVE_CART_ITEM,
    REMOVE_ALL_CART_ITEMS,
    SET_TOAST_MESSAGE,
} from './constants';
const initState = {
    carts: [],
    cartItem: {},
    toastMessage: '',
};

function reducer(state, action) {
    switch (action.type) {
        case SET_CART_ITEM:
            return {
                ...state,
                cartItem: action.payload,
            };
        case ADD_CART_ITEM:
            let keepCard = {};
            let isDuplicateId = false;
            let payload = action.payload;
            // Remove carts if duplicate id
            let cartsFilters = Array.from(state.carts).filter((cart) => {
                // Keep item card to add quantity
                if (cart.productVariantId === payload.productVariantId) {
                    keepCard = cart;
                    isDuplicateId = true;
                }
                return cart.productVariantId !== payload.productVariantId;
            });
            if (isDuplicateId) {
                let quantityOld = keepCard.quantity;
                let quantityPayload = payload.quantity;
                payload = {
                    ...payload,
                    quantity: quantityOld + quantityPayload,
                };
            }
            cartsFilters.push(payload);
            return {
                ...state,
                carts: cartsFilters,
            };
        case REMOVE_CART_ITEM:
            return {
                ...state,
                carts: Array.from(state.carts).filter((cart) => {
                    let isExist = false;
                    if (cart.productVariantId === action.payload.productVariantId) {
                        isExist = true;
                    }
                    return !isExist;
                }),
            };
        case REMOVE_ALL_CART_ITEMS:
            return {
                ...state,
                carts: [],
            };
        case SET_TOAST_MESSAGE:
            return {
                ...state,
                toastMessage: action.payload,
            };
        default:
            throw new Error('Invalid');
    }
}

export { initState };
export default reducer;
