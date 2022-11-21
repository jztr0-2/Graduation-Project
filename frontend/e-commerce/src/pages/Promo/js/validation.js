/**
 * Kiểm tra xem dữ liệu có để trống hay không
 * @param {Object} obj
 * @returns {Object}
 */
const validate = (obj) => {
    const empty = {};
    for (const [key, value] of Object.entries(obj)) {
        if (!value) {
            empty[key] = "Can't be left blank";
        }
    }
    return empty;
};

/**
 * Kiểm tra dữ liệu có phải là số hay không
 * @param {Object} obj
 * @returns {Object}
 */

const validateNumber = (obj) => {
    const notANumber = {};
    for (const [key, value] of Object.entries(obj)) {
        if (isNaN(value)) {
            notANumber[key] = 'Not a number';
        }
    }
    return notANumber;
};

export { validate, validateNumber };
