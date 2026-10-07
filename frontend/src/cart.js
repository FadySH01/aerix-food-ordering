export const money = value => new Intl.NumberFormat('en-US',{style:'currency',currency:'USD'}).format(value);
export function normalizeCart(value) {
  if(!Array.isArray(value)) return [];
  const seen=new Set();
  return value.filter(i => i && typeof i.id==='string' && i.id.length<100 && Number.isInteger(i.quantity) && i.quantity>0 && i.quantity<=99 && !seen.has(i.id) && seen.add(i.id)).map(({id,quantity})=>({id,quantity}));
}
export function cartTotal(lines) { return lines.reduce((sum,i)=>sum+Math.round(Number(i.food.price)*100)*i.quantity,0)/100; }
export function changeQuantity(cart,id,delta) {
  const existing=cart.find(i=>i.id===id);
  if(!existing) return delta>0?[...cart,{id,quantity:Math.min(99,delta)}]:cart;
  return cart.map(i=>i.id===id?{...i,quantity:Math.min(99,i.quantity+delta)}:i).filter(i=>i.quantity>0);
}
