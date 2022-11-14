import React, { useState } from 'react';
import classNames from 'classnames/bind';
import styles from '../Form.module.scss';

const cx = classNames.bind(styles);

function DetailsTable() {
  const [detailsInfo, setDetailsInfo] = useState([]);

  const addDetails = (e) => {
    const { detailsKey, detailsDescription } = document.forms[1];
    if (e.key === 'Enter') {
      const details = {
        keys: detailsKey.value,
        description: detailsDescription.value,
      }
      
      setDetailsInfo([...detailsInfo,details]);
      detailsKey.value = '';
      detailsDescription.value = '';
    }
  }

  function CreateDetailsElement(props) {
    return (
      <div key={props.details.keys}>
        <div className={cx('list-details-cover')}>
        <div className={cx('details-key')}>{props.details.keys}</div>
        <div className={cx('details-description')}>{props.details.description}</div>
        </div>
      </div>
    );
  }

  return (
    <div className={cx('form-group')}>
        <div className={cx('group-details')}>
        <div className={cx('list-details')}>
            {detailsInfo.map((item, index) => (
              <CreateDetailsElement 
                key = {index}
                details = {item}
                call={function(){}}
              />
            ))}
        </div>
        <div className={cx('products-details')}>
            <input type="text" onKeyDown={addDetails} name="detailsKey" className={cx('details-key')} placeholder="Key"/>
            <input type="text" onKeyDown={addDetails} name="detailsDescription" className={cx('details-value')} placeholder="Value"/>
        </div>
        </div>
    </div>
  );
}

export default DetailsTable;
