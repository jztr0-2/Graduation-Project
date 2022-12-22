import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDatabase } from '@fortawesome/free-solid-svg-icons';

function DataEmpty({ error, isPadding, message, ...props }) {
    return (
        <div className={isPadding ? isPadding : null}>
            <div className="flex flex-col items-center">
                <FontAwesomeIcon icon={faDatabase} className="py-8 text-6xl" />
                <h3 className="text-4xl pb-6 font-bold">{message ? message : 'Data Empty'}</h3>
            </div>
        </div>
    );
}

export default DataEmpty;
