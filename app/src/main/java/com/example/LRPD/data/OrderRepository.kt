package com.example.LRPD.data

import com.example.LRPD.model.HistoryLog
import com.example.LRPD.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class OrderRepository {

    private val db = FirebaseFirestore.getInstance()
    private val ordersCollection = db.collection("orders")
    private val historyCollection = db.collection("history")

    // ===== PEDIDOS =====
    fun listenOrders(onUpdate: (List<Order>) -> Unit): ListenerRegistration {
        return ordersCollection.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener
            val list = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            onUpdate(list)
        }
    }

    fun saveOrder(order: Order) {
        ordersCollection.document(order.code).set(order)
    }

    // ===== HISTORIAL =====
    fun listenHistory(onUpdate: (List<HistoryLog>) -> Unit): ListenerRegistration {
        return historyCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener
                val list = snapshot.documents.mapNotNull { it.toObject(HistoryLog::class.java) }
                onUpdate(list)
            }
    }

    fun saveLog(log: HistoryLog) {
        historyCollection.add(log)
    }
}