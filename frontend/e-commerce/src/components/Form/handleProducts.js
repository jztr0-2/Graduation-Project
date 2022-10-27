import * as request from '~/utils/http';

export const createNewProduct = (e) => {
    e.preventDefault();
    var { productName, selectStatus, selectCategory, description } = document.forms[1];

    const product = {
      name: productName.value,
      status: Number(selectStatus.value),
      description: description.value,
      categoryId: Number(selectCategory.value),
    }

    console.log(product);
    request.post("/admin/products", product)
      .then(function (response) {
        console.log(response)
      })
      .catch(function (error) {
        console.log(error);
      })
};

export const resetProduct = (e) => {
    document.forms[1].reset();
};