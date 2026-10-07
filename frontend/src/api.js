const base=import.meta.env.VITE_API_URL || '';
export async function api(path,options={}) {
  let response;
  try { response=await fetch(base+path,{...options,headers:{...(options.body?{'Content-Type':'application/json'}:{}),...options.headers}}); }
  catch { throw new Error('We could not reach the restaurant. Please check your connection and try again.'); }
  const data=response.status===204?null:await response.json().catch(()=>null);
  if(!response.ok) { const error=new Error(data?.message || (response.status===401?'Your sign-in has expired. Please sign in again.':'Something went wrong. Please try again.')); error.fields=data?.fields; error.status=response.status; throw error; }
  return data;
}
