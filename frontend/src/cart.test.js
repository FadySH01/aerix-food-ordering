import test from 'node:test';
import assert from 'node:assert/strict';
import {cartTotal,changeQuantity,normalizeCart} from './cart.js';
test('decimal prices add without floating-point drift',()=>assert.equal(cartTotal([{food:{price:0.1},quantity:1},{food:{price:0.2},quantity:1}]),0.3));
test('quantity controls add, cap and remove items',()=>{assert.deepEqual(changeQuantity([], 'a',1),[{id:'a',quantity:1}]);assert.deepEqual(changeQuantity([{id:'a',quantity:1}],'a',-1),[]);assert.equal(changeQuantity([{id:'a',quantity:99}],'a',1)[0].quantity,99);});
test('persisted cart rejects malformed data and duplicates',()=>assert.deepEqual(normalizeCart([{id:'a',quantity:2},{id:'a',quantity:3},{id:'b',quantity:-1},null]),[{id:'a',quantity:2}]));
