import React, { memo } from 'react'

const Logo: React.FC = () => {
    return (
        <div> 
            <h1 className="text-3xl font-bold title-font text-gray-800">
            <span className="text-indigo-600 title-font">T</span>Auth
        </h1>
        </div>
    )
}

export default memo(Logo)