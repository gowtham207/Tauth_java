import React from 'react'

type props = {
    title:string;
    type: 'email'| 'password'
    name:string
    placeholder:string
    required:boolean
    className?: string
    value: string
    onchange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};


const CustomFormInput:React.FC<props> = ({
    title,
    type,
    name,
    placeholder,
    required,
    className,
    onchange,
    value
}) => {
  return (
    <div>
        <label  className="block text-gray-700 font-semibold mb-2">
            {title}
        </label>
        <input
              type={type}
              name={name}
              value={value}
              onChange={onchange}
              placeholder={placeholder}
              required={required}
              className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 ${className}`}
            />
    </div>
  )
}

export default CustomFormInput