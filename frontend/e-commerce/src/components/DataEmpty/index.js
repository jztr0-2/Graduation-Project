import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDatabase } from '@fortawesome/free-solid-svg-icons';

function DataEmpty({ error, ...props }) {
    return (
        <div className="flex flex-col items-center">
            <FontAwesomeIcon icon={faDatabase} className="py-8 text-6xl" />
            <h3 className="text-4xl pb-6 font-bold">Data Empty</h3>
        </div>
    );
}

export default DataEmpty;
