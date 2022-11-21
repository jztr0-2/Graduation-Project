import React, { useRef, useState } from 'react';
import styles from './Form.module.scss';
import Category from './Category';
import DetailsTable from './Details/details';
import * as request from '~/utils/http';
import classNames from 'classnames/bind';

const cx = classNames.bind(styles);

function FormProducts({fields}) {
  const [isCheck, setIsChecked] = useState(false);
  const ref = useRef('')

  const onChange = () => {
    setIsChecked(!isCheck)
  }

  const createNewProduct = (e) => {
    e.preventDefault();
    console.log(ref.current);
    var { productName, selectStatus, selectCategory, description } = document.forms[1];
  
    const product = {  
      name: productName.value,
      status: Number(selectStatus.value),
      description: description.value,
      categoryId: Number(selectCategory.value),
    }
  
    request.post("/admin/products", product)
      .then(function (response) {
        console.log(response)
      })
      .catch(function (error) {
        console.log(error);
      })
  };

  const resetProduct = (e) => {
    document.forms[1].reset();
  };

  return (
    <form action="" className={cx('form-products')}>
      <div className={cx('form-information')}>
        <div className={cx('form-content')}>
          <div className={cx('form-group')}>
            <input ref={ref} type="text" className={cx('products-name')} name="productName" placeholder="Product's Name"/>
          </div>
  
          <div className={cx('form-group')} id="productStatus">
            <select name="selectStatus" id="" className={cx('products-status')}>
                <option disabled selected>Select Status</option>
                <option value="0" ref={ref}>Available</option>
                <option value="1" ref={ref}>Sold Out</option>
            </select>
          </div>
  
          <div className={cx('form-group')} id="productCategory">
            {/* {<Category />} */}
          </div>

          
          <div className={cx('form-group')}>
            <textarea name="description" ref={ref} type="text" className={cx('products-description')} placeholder="Product's Description"/>
          </div>

          <div className={cx('form-group')}>
            <div className={cx('checkbox-related')}>
              <input  type="checkbox" className={cx('products-related')} placeholder="Product's Description" onChange={onChange} checked={isCheck}/>
              <span> No related products</span>
            </div>
          </div>

          {isCheck && <DetailsTable />}
        </div>

        <div className={cx('form-img')}>
          <div className={cx('form-group')}>
            <div className={cx('images')}></div>
            <input name='image' type="file" className={cx('products-name')} />
          </div>
        </div>
      </div>

      <div className={cx('form-cta')}>
        <button type="button" className={cx('btn', 'btn-create')} onClick={createNewProduct}>Create</button>
        <button type="button" className={cx('btn', 'btn-update')}>Update</button>
        <button type="button" className={cx('btn', 'btn-delete')}>Delete</button>
        <button type="button" className={cx('btn', 'btn-reset')} onClick={resetProduct}>Reset</button>
      </div>
    </form>
  );

}

export default FormProducts;
