import * as request from '~/utils/http';
import styles from './Form.module.scss';
import classNames from 'classnames/bind';

const cx = classNames.bind(styles);

const state = [];

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

export const checkRelated = () => {
  const related = document.querySelector('.Form_products-related__Gdfik');
  const details = document.querySelector('.Form_group-details__gSFvl');
  
  if (related.checked) {
    details.style.display = 'block';
  } else {
    details.style.display = 'none';
  }
};

export const addDetails = (e) => {
  var { detailsKey, detailsDescription } = document.forms[1];

  if (e.key === 'Enter') {
    const details = {
      key: detailsKey.value,
      description: detailsDescription.value,
    }
    state.push(details);
    detailsKey.value = '';
    detailsDescription.value = '';
  }
}

export const CreateDetailsElement = () => {
  state.map((item) => (
    <div className={cx('list-details-cover')}>
      <div className={cx('details-key')}>${item.key}</div>
      <div className={cx('details-value')}>${item.description}</div>
    </div>
  ));
}; 
