// import React, { useState } from 'react';
// import './Design.css';

// interface FormInterface {
//   email: string;
//   password: string;
// }

// function LoginForm() {
//   const [message, setMessage] = useState('');

//   const handleSubmit = async (event: React.FormEvent) => {
//     event.preventDefault();
//     const form = event.target as HTMLFormElement;
//     const formData = new FormData(form);
//     const dataToSend: FormInterface = {
//       email: formData.get('email') as string,
//       password: formData.get('password') as string,
//     };

//     try {
//       const response = await fetch('http://localhost:8080/api/user/login', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(dataToSend),
//       });

//       const data = await response.json();
//       console.log(data, typeof(data));
//       if (response.ok && data.token) {
//         localStorage.setItem('token', data.token);
//         setMessage('Login successful');
//         // The user is allowed
//         console.log('Hi and Hello User is allowed');
//       } else {
//         setMessage('Login failed');
//         // The user is not allowed
//         console.log('Sorry you are not allowed!!');
//       }
//     } catch (error) {
//       console.error('Error fetching data: ', error);
//       setMessage('Login failed');
//     }
//   };

//   return (
//     <form className="ml-5 mt-5" onSubmit={handleSubmit}>
//       <link rel="stylesheet" href="Design.css"></link>
//       <div className="form-group row">
//         <div className="col-sm-5">
//           <input name="email" type="email" placeholder="Email Address" className="form-control"></input>
//         </div>
//       </div>
//       <div className="form-group row">
//         <div className="col-sm-5">
//           <input name="password" type="password" placeholder="Password" className="form-control"></input>
//         </div>
//       </div>
//       <div className="form-group row mt-4">
//         <div className="col-sm-5">
//           <button type="submit" className="btn btn-primary btn-block">Sign in</button>
//         </div>
//       </div>
//       <p>{message}</p>
//     </form>
//   );
// }

// export default LoginForm;
